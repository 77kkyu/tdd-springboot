package com.example.tddspringboot.membership.service;

import com.example.tddspringboot.membership.domain.Membership;
import com.example.tddspringboot.membership.domain.MembershipType;
import com.example.tddspringboot.membership.exception.MembershipErrorResult;
import com.example.tddspringboot.membership.exception.MembershipException;
import com.example.tddspringboot.membership.repository.MembershipRepository;
import com.example.tddspringboot.membership.model.MembershipAdd;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {

    @Mock
    private MembershipRepository membershipRepository;

    @InjectMocks
    private MembershipService membershipService;

    private final String userId = "userId";
    private final MembershipType membershipType = MembershipType.KAKAO;
    private final Integer point = 1000;

    @Test
    public void 멤버십등록실패_이미존재() {
        // given
        doReturn(Membership.builder().build())
                .when(membershipRepository)
                .findByUserIdAndMembershipType(userId, membershipType);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> membershipService.addMembership(userId, membershipType, point));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);

    }

    @Test
    public void 멤버십등록성공() {

        // given
        doReturn(null)
                .when(membershipRepository)
                .findByUserIdAndMembershipType(userId, membershipType);

        doReturn(membership())
                .when(membershipRepository)
                .save(any(Membership.class));

        // when
        final MembershipAdd result = membershipService.addMembership(userId, membershipType, point);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.KAKAO);

        // verify
        verify(membershipRepository, times(1)).findByUserIdAndMembershipType(userId, membershipType);
        verify(membershipRepository, times(1)).save(any(Membership.class));
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
