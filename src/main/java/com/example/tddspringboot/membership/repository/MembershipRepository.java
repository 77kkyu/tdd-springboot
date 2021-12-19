package com.example.tddspringboot.membership.repository;

import com.example.tddspringboot.membership.domain.Membership;
import com.example.tddspringboot.membership.domain.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findByUserIdAndMembershipType(final String userId, final MembershipType membershipType);

}
