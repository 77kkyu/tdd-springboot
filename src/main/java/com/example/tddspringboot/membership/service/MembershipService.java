package com.example.tddspringboot.membership.service;

import com.example.tddspringboot.membership.domain.Membership;
import com.example.tddspringboot.membership.domain.MembershipType;
import com.example.tddspringboot.membership.exception.MembershipErrorResult;
import com.example.tddspringboot.membership.exception.MembershipException;
import com.example.tddspringboot.membership.repository.MembershipRepository;
import com.example.tddspringboot.membership.response.MembershipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipResponse addMembership(String userId, MembershipType membershipType, Integer point) {

        Membership resutl = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);
        if (resutl != null) {
            throw new MembershipException(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);
        }

        Membership membership = Membership.builder()
                .userId(userId)
                .point(point)
                .membershipType(membershipType)
                .build();

        Membership saveResult =  membershipRepository.save(membership);

        return MembershipResponse.builder()
                .id(saveResult.getId())
                .membershipType(saveResult.getMembershipType())
                .build();
    }

}
