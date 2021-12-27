package com.example.tddspringboot.membership.model;

import com.example.tddspringboot.membership.domain.MembershipType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class MembershipDetail {

    private final Long id;
    private final MembershipType membershipType;
    private final LocalDateTime localDateTime;
    private final Integer point;

}
