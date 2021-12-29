package com.example.tddspringboot.membership.service;

import com.example.tddspringboot.membership.domain.Membership;
import com.example.tddspringboot.membership.domain.MembershipType;
import com.example.tddspringboot.membership.exception.MembershipErrorResult;
import com.example.tddspringboot.membership.exception.MembershipException;
import com.example.tddspringboot.membership.model.MembershipDetail;
import com.example.tddspringboot.membership.repository.MembershipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

// 조회
@ExtendWith(MockitoExtension.class)
public class MembershipSearchServiceTest {

    @Mock
    private MembershipRepository membershipRepository;

    @InjectMocks
    private MembershipService membershipService;

    private final String userId = "userId";
    private final MembershipType membershipType = MembershipType.KAKAO;
    private final Integer point = 1000;

    @Test
    public void 멤버쉽목록조회() {

        // given
        doReturn(Arrays.asList(
                Membership.builder().build()
                , Membership.builder().build()
                , Membership.builder().build()
        )).when(membershipRepository).findAllByUserId(userId);

        // when
        final List<MembershipDetail> result = membershipService.getMembershipList(userId);

        // then
        assertThat(result.size()).isEqualTo(3);

    }

    @Test
    public void 멤버십상세조회실패_존재하지않음() {

        // given
        doReturn(null).when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> membershipService.getMembership(userId, membershipType));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);

    }

    @Test
    public void 멤버십상세조회성공() {

        // given
        doReturn(Membership.builder()
                .id(-1L)
                .membershipType(MembershipType.KAKAO)
                .point(point)
                .createdAt(LocalDateTime.now())
                .build()
        ).when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);

        // when
        final MembershipDetail result = membershipService.getMembership(userId, membershipType);

        // then
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.KAKAO);
        assertThat(result.getPoint()).isEqualTo(point);

    }

}
