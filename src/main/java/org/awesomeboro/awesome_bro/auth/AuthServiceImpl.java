package org.awesomeboro.awesome_bro.auth;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.constant.ErrorCode;
import org.awesomeboro.awesome_bro.dto.user.TokenDto;
import org.awesomeboro.awesome_bro.exception.PasswordException;
import org.awesomeboro.awesome_bro.security.JwtFilter;
import org.awesomeboro.awesome_bro.security.TokenProvider;
import org.awesomeboro.awesome_bro.user.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AuthRepository authRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public TokenDto getToken(User user){
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            // 해당 객체를 SecurityContextHolder에 저장하고
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
            String jwt = tokenProvider.createToken(authentication);
            HttpHeaders httpHeaders = new HttpHeaders();
            // response header에 jwt token에 넣어줌
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK).getBody();
        }catch (BadCredentialsException e){
            throw  new PasswordException(ErrorCode.WRONG_PASSWORD);
        }catch (InternalAuthenticationServiceException e) {
            if(e.getCause() instanceof RuntimeException){
                throw (RuntimeException) e.getCause();
            }
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Authority> findById(long l) {
        return authRepository.findById(l);
    }
}
