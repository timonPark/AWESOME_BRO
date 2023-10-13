package org.awesomeboro.awesome_bro.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.awesomeboro.awesome_bro.auth.CustomUserDetails;
import org.awesomeboro.awesome_bro.dto.user.CustomUserDetailDto;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;
import org.awesomeboro.awesome_bro.user.User;
import org.awesomeboro.awesome_bro.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static org.awesomeboro.awesome_bro.constant.ErrorCode.UNDEFINED_EMAIL;

@Component
public class TokenProvider implements InitializingBean {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;


    /**
     * 토큰제공 메서드
     *
     * @param userRepository
     * @param secret
     * @param tokenValidityInSeconds
     */
    public TokenProvider(
            UserRepository userRepository, @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.userRepository = userRepository;
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    /**
     * base64로 인코딩된 secret값을 decode해서 key변수에 할당
     */
    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Authentication 객체의 권한정보를 이용해서 토큰을 생성하는 메서드
     * @param authentication
     * @return
     */
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 토큰의 expire 시간을 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities) // 정보 저장
                .signWith(SignatureAlgorithm.HS512, key) // 사용할 암호화 알고리즘과 , signature 에 들어갈 secret값 세팅
                .setExpiration(validity) // set Expire Time 해당 옵션 안넣으면 expire안함
                .compact();
    }

    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    @Transactional
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String userEmail = claims.getSubject();
        User userFromDb = userRepository.findByEmail(userEmail).orElseThrow(()-> new UserNotFoundException(UNDEFINED_EMAIL));
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        CustomUserDetailDto customUserDetailDto = new CustomUserDetailDto(userFromDb);
        CustomUserDetails principal = new CustomUserDetails(customUserDetailDto);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰의 유효성 검증을 수행
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
