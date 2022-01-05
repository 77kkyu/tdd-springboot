package com.example.tddspringboot.membership.service;

import com.example.tddspringboot.membership.domain.Membership;
import com.example.tddspringboot.membership.domain.MembershipType;
import com.example.tddspringboot.membership.exception.MembershipErrorResult;
import com.example.tddspringboot.membership.exception.MembershipException;
import com.example.tddspringboot.membership.repository.MembershipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RatePointServiceTest {

    @Mock
    private MembershipRepository membershipRepository;

    @Mock
    private RatePointService ratePointService;

    @InjectMocks
    private MembershipService membershipService;

    private final String userId = "userId";
    private final MembershipType membershipType = MembershipType.KAKAO;
    private final Integer point = 1000;
    private final Long membershipId = 1L;

    @Test
    public void 멤버십적립실패_존재하지않음() {

        // given
        doReturn(Optional.empty()).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () ->
                membershipService.accumulateMembershipPoint(membershipId, userId, 1000));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);

    }

    @Test
    public void 멤버십적립실패_본인이아님() {

        // given
        final Membership membership = membership();
        doReturn(Optional.of(membership)).when(membershipRepository).findById(membershipId);
        // when
        final MembershipException result = assertThrows(MembershipException.class, () ->
                membershipService.accumulateMembershipPoint(membershipId, "notowner", 1000));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);

    }

    @Test public void 멤버십적립성공() {

        // given
        final Membership membership = membership();
        doReturn(Optional.of(membership)).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () ->
                membershipService.accumulateMembershipPoint(membershipId, userId, 1000));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);

    }


    private Membership membership() {
        return Membership.builder()
                .id(-1L)
                .userId(userId)
                .point(point)
                .membershipType(MembershipType.KAKAO)
                .build();
    }

}
