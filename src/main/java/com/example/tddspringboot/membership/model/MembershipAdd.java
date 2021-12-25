package com.example.tddspringboot.membership.model;

import com.example.tddspringboot.membership.domain.MembershipType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MembershipAdd {

    private final Long id;
    private final MembershipType membershipType;

}
