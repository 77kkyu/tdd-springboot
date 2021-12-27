package com.example.tddspringboot.membership.repository;

import com.example.tddspringboot.membership.domain.Membership;
import com.example.tddspringboot.membership.domain.MembershipType;
import com.example.tddspringboot.membership.model.MembershipDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findByUserIdAndMembershipType(final String userId, final MembershipType membershipType);

    List<Membership> findAllByUserId(final String userId);

}
