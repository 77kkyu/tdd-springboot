package com.example.tddspringboot.membership.service;

import com.example.tddspringboot.membership.domain.Membership;
import com.example.tddspringboot.membership.domain.MembershipType;
import com.example.tddspringboot.membership.exception.MembershipErrorResult;
import com.example.tddspringboot.membership.exception.MembershipException;
import com.example.tddspringboot.membership.model.MembershipDetail;
import com.example.tddspringboot.membership.repository.MembershipRepository;
import com.example.tddspringboot.membership.model.MembershipAdd;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mbeans.MemoryUserDatabaseMBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final PointService pointService;

    public MembershipAdd addMembership(String userId, MembershipType membershipType, Integer point) {

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

        return MembershipAdd.builder()
                .id(saveResult.getId())
                .membershipType(saveResult.getMembershipType())
                .build();
    }

    public List<MembershipDetail> getMembershipList(final String userId) {

        final List<Membership> membershipList = membershipRepository.findAllByUserId(userId);

        return membershipList.stream()
                .map(v -> MembershipDetail.builder()
                        .id(v.getId())
                        .membershipType(v.getMembershipType())
                        .point(v.getPoint())
                        .localDateTime(v.getCreatedAt())
                        .build()
                ).collect(Collectors.toList());
    }

    public MembershipDetail getMembership(final String userId, final MembershipType membershipType) {

        final Membership findResult = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);
        if (findResult == null) {
            throw new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);
        }

        return MembershipDetail.builder()
                .id(findResult.getId())
                .membershipType(findResult.getMembershipType())
                .point(findResult.getPoint())
                .localDateTime(findResult.getCreatedAt())
                .build();
    }

    public void removeMembership(final Long membershipId, final String userId) {
        final Optional<Membership> optionalMembership = membershipRepository.findById(membershipId);
        final Membership membership = optionalMembership.orElseThrow(() ->
                new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND));
        if (!membership.getUserId().equals(userId)) {
            throw new MembershipException(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);
        }

        membershipRepository.deleteById(membershipId);

    }

    @Transactional
    public void accumulateMembershipPoint(final Long membershipId, final String userId, final int amount) {

        final Optional<Membership> optionalMembership = membershipRepository.findById(membershipId);
        final Membership membership = optionalMembership.orElseThrow(() ->
                new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND));

        if (!membership.getUserId().equals(userId)) {
            throw new MembershipException(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);
        }

        final int additionalAmount = pointService.calculateAmount(amount);
        membership.setPoint(additionalAmount+membership.getPoint());

    }

}
