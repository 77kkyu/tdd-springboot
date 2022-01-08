package com.example.tddspringboot.membership.controller;

import com.example.tddspringboot.membership.domain.MembershipType;
import com.example.tddspringboot.membership.request.MembershipRequest;
import com.example.tddspringboot.membership.service.MembershipService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.tddspringboot.membership.codes.MembershipConstants.USER_ID_HEADER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RatePointControllerTest {

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
    public void 멤버십적립실패_사용자식별값이헤더에없음() throws Exception {

        // given
        final String url = "/api/v1/membership/-1/accumulate";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(membershipRequest(1000, MembershipType.KAKAO)))
                        .contentType(MediaType.APPLICATION_JSON) );

        // then
        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    public void 멤버십적립실패_포인트가음수() throws Exception {

        // given
        final String url = "/api/v1/membership/-1/accumulate";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(USER_ID_HEADER, "12345")
                        .content(gson.toJson(membershipRequest(-1, MembershipType.KAKAO)))
                        .contentType(MediaType.APPLICATION_JSON) );

        // then
        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    public void 멤버십적립성공() throws Exception {

        // given
        final String url = "/api/v1/membership/-1/accumulate";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(USER_ID_HEADER, "12345")
                        .content(gson.toJson(membershipRequest(1000)))
                        .contentType(MediaType.APPLICATION_JSON) );

        // then
        resultActions.andExpect(status().isNoContent());

    }

    private MembershipRequest membershipRequest(final Integer point) {
        return MembershipRequest.builder()
                .point(point)
                .build();
    }

    private MembershipRequest membershipRequest(final Integer point, final MembershipType membershipType) {
        return MembershipRequest.builder()
                .point(point)
                .membershipType(membershipType)
                .build();
    }


}
