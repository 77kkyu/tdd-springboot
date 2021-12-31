package com.example.tddspringboot.membership.controller;

import com.example.tddspringboot.membership.domain.MembershipType;
import com.example.tddspringboot.membership.exception.MembershipErrorResult;
import com.example.tddspringboot.membership.exception.MembershipException;
import com.example.tddspringboot.membership.model.MembershipDetail;
import com.example.tddspringboot.membership.service.MembershipService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static com.example.tddspringboot.membership.codes.MembershipConstants.USER_ID_HEADER;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MembershipSearchControllerTest {

    @InjectMocks
    private MembershipController membershipController;

    @Mock
    private MembershipService membershipService;

    private MockMvc mockMvc;

    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(membershipController)
                .build();
    }

    @Test
    public void 멤버십목록조회실패_사용자식별값이헤더에없음() throws Exception {

        // given
        final String url = "/api/v1/membership/list";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    public void 멤버십상세조회실패_멤버십타입이파라미터에없음() throws Exception {

        // given
        final String url = "/api/v1/membership";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .header(USER_ID_HEADER, "12345")
                        .param("membershipType", "empty")
        );

        // then
        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    public void 멤버십상세조회실패_멤버십이존재하지않음() throws Exception {

        // given
        final String url = "/api/v1/membership";
        doReturn(new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND))
                .when(membershipService)
                .getMembership("12345", MembershipType.KAKAO);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .header(USER_ID_HEADER, "12345")
                        .param("membershipType", MembershipType.KAKAO.name())
        );

        // then
        resultActions.andExpect(status().isNotFound());

    }

    @Test
    public void 멤버십상세조회성공() throws Exception {

        // given
        final String url = "/api/v1/membership";
        doReturn(
                MembershipDetail.builder().build()
        ).when(membershipService).getMembership("12345", MembershipType.KAKAO);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .header(USER_ID_HEADER, "12345")
                        .param("membershipType", MembershipType.KAKAO.name())
        );

        // then
        resultActions.andExpect(status().isOk());

    }

    @Test
    public void 멤버십목록조회성공() throws Exception {

        // given
        final String url = "/api/v1/membership/list";
        doReturn(Arrays.asList(
                MembershipDetail.builder().build()
                , MembershipDetail.builder().build()
                , MembershipDetail.builder().build()
        )).when(membershipService).getMembershipList("12345");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .header(USER_ID_HEADER, "12345")
        );

        // then
        resultActions.andExpect(status().isOk());

    }

}
