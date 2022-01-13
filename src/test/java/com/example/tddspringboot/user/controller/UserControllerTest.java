package com.example.tddspringboot.user.controller;

import com.example.tddspringboot.user.domain.User;
import com.example.tddspringboot.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // 싱클톤
@WebMvcTest(UserContorller.class) // @ControllerAdvice, @JsonComponent, @Filter 자동 빈 등록
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void 유저_생성() throws Exception {

        String name = "77kkyu";
        given(userService.add(any(User.class)))
                .willReturn(User.builder()
                        .name(name)
                        .build()
                );

        mockMvc.perform(MockMvcRequestBuilders.post("/user/"+name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(name)); // 유저등록 후 반환되는 name필드 값 비교

    }

    @Test
    public void 유저_조회() throws Exception {

        given(userService.getOne(anyLong()))
                .willReturn(User.builder()
                        .name("77kkyu")
                        .build()
                );

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("77kkyu"));

    }

    @Test
    public void 유저_없음_예외처리() throws Exception {
        given(userService.getOne(anyLong())).willThrow(new EntityNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(status().isNotFound());
    }


}
