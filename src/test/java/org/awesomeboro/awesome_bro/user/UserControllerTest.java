package org.awesomeboro.awesome_bro.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.awesomeboro.awesome_bro.dto.user.UserSignUpRequestDto;
import org.awesomeboro.awesome_bro.dto.user.UserInfoDto;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private  MockMvc mvc;
    @Autowired
    private  ObjectMapper objectMapper;
    @MockBean
    private UserService userService;


//    public UserControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper,  UserService userService) {
//        this.mvc = mvc;
//        this.objectMapper = objectMapper;
//        this.userService = userService;
//    }

    @Test
    void createUser() {
    }

    @Test
    void login() {
    }

    @Test
    void getUser() {
    }

    @DisplayName("[API][PUT] 사용자 정보 수정")
    @Test
    void updateUser() throws Exception {
        // given

        Long userId = 1L;
        UserInfoDto user = UserInfoDto.builder()
                .id(userId)
                .name("이관영")
                .nickname("이관영님")
                .phoneNumber("01012345678")
                .email("rhksdud23000@naver.com")
                .profilePicture("https://avatars.githubusercontent.com/u/62266294?v=4")
                .loginType("normal")
                .socialId("asfasf1qadsfdfasdf")
                .build();
        when(userService.updateUser(any(UserInfoDto.class), anyLong())).thenAnswer(ele-> {

            return user;
        });


        // when
        MvcResult result = mvc.perform(patch("/user/{id}",userId)
                .content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(userId))
                .andExpect(jsonPath("$.data.name").value(user.getName()))
                .andExpect(jsonPath("$.data.nickname").value(user.getNickname()))
                .andExpect(jsonPath("$.data.phoneNumber").value(user.getPhoneNumber()))
                .andExpect(jsonPath("$.data.loginType").value(user.getLoginType()))
                .andExpect(jsonPath("$.data.socialId").value(user.getSocialId()))
                .andExpect(jsonPath("$.data.email").value(user.getEmail()))
                .andExpect(jsonPath("$.data.profilePicture").value(user.getProfilePicture()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(IsNull.nullValue()))
                .andReturn();
        // then
        /*
        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ApiResponse apiResponse = objectMapper.readValue(responseBody, ApiResponse.class);
        UserSignUpResponseDto returnedUser = objectMapper.convertValue(apiResponse.getData(), UserSignUpResponseDto.class);

        assertEquals(user.getId(), returnedUser.getId());
        assertEquals(user.getName(), returnedUser.getName());
        assertEquals(user.getNickname(), returnedUser.getNickname());
        assertEquals(user.getPhoneNumber(), returnedUser.getPhoneNumber());
        assertEquals(user.getLoginType(), returnedUser.getLoginType());
        assertEquals(user.getSocialId(), returnedUser.getSocialId());
        assertEquals(user.getEmail(), returnedUser.getEmail());
        assertEquals(user.getProfilePicture(), returnedUser.getProfilePicture());

         */

    }

    @Test
    void socialLoginAndSignUp() {
    }
}