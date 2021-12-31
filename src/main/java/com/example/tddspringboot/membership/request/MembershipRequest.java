package com.example.tddspringboot.membership.request;

import com.example.tddspringboot.membership.codes.ValidationGroups;
import com.example.tddspringboot.membership.domain.MembershipType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MembershipRequest {

    @NotNull(groups = {ValidationGroups.MembershipAddMarker.class, ValidationGroups.MembershipAccumulateMarker.class})
    @Min(value = 0, groups = {ValidationGroups.MembershipAddMarker.class, ValidationGroups.MembershipAccumulateMarker.class})
    private final Integer point;

    @NotNull(groups = {ValidationGroups.MembershipAddMarker.class})
    private final MembershipType membershipType;

}

