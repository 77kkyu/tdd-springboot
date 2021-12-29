package com.example.tddspringboot.membership.repository;

import com.example.tddspringboot.membership.domain.Membership;
import com.example.tddspringboot.membership.domain.MembershipType;
import com.example.tddspringboot.membership.model.MembershipDetail;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest // JPA Repository들에 대한 빈들을 등록하여 단위 테스트의 작성을 가능하게 함
public class MembershipRepositoryTest {

    @Autowired
    private MembershipRepository membershipRepository;

    @Test
    public void 멤버십등록() {
        final Membership membership = Membership.builder()
                .userId("kkyu")
                .point(1000)
                .membershipType(MembershipType.KAKAO)
                .build();
        final Membership result = membershipRepository.save(membership);

        System.out.println("================================================");

        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo("kkyu");
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.KAKAO);
        assertThat(result.getPoint()).isEqualTo(1000);

    }

    @Test
    public void MembershipRepositoryIsNotNull() {
        assertThat(membershipRepository).isNotNull();
    }

    @Test
    public void 멤버십조회_사이즈가2() {

        // given
        final Membership kakaoMemberShip = Membership.builder()
                .userId("hash")
                .membershipType(MembershipType.KAKAO)
                .point(1000)
                .build();

        final Membership naverMemberShip = Membership.builder()
                .userId("hash")
                .membershipType(MembershipType.NAVER)
                .point(1000)
                .build();

        membershipRepository.save(kakaoMemberShip);
        membershipRepository.save(naverMemberShip);

        // when
        List<Membership> result = membershipRepository.findAllByUserId("hash");

        // then
        AssertionsForClassTypes.assertThat(result.size()).isEqualTo(2);

    }

    @Test
    public void 멤버십추가후삭제() {

        // given
        final Membership kakaoMembership = Membership.builder()
                .userId("hash")
                .membershipType(MembershipType.KAKAO)
                .point(1000)
                .build();

        final Membership savedMembership = membershipRepository.save(kakaoMembership);

        // when
        membershipRepository.deleteById(savedMembership.getId());

    }

}
