package com.example.tddspringboot.membership.controller;

import com.example.tddspringboot.membership.codes.ValidationGroups;
import com.example.tddspringboot.membership.domain.MembershipType;
import com.example.tddspringboot.membership.handle.DefaultRestController;
import com.example.tddspringboot.membership.model.MembershipDetail;
import com.example.tddspringboot.membership.request.MembershipRequest;
import com.example.tddspringboot.membership.model.MembershipAdd;
import com.example.tddspringboot.membership.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.example.tddspringboot.membership.codes.MembershipConstants.USER_ID_HEADER;

@RestController
@RequiredArgsConstructor
public class MembershipController extends DefaultRestController {

    private final MembershipService membershipService;

    @PostMapping("/api/v1/membership")
    public ResponseEntity<MembershipAdd> addMembership(
            @RequestHeader(USER_ID_HEADER) final String userId
            , @RequestBody @Validated(ValidationGroups.MembershipAddMarker.class) final MembershipRequest membershipRequest
            ) {

        MembershipAdd membershipResponse = membershipService.addMembership(
                userId, membershipRequest.getMembershipType(), membershipRequest.getPoint());

        return ResponseEntity.status(HttpStatus.CREATED).body(membershipResponse);

    }

    @GetMapping("/api/v1/membership/list")
    public ResponseEntity<List<MembershipDetail>> getMembershipList(@RequestHeader(USER_ID_HEADER) final String userId) {
        return ResponseEntity.ok(membershipService.getMembershipList(userId));
    }

    @GetMapping("/api/v1/membership")
    public ResponseEntity<MembershipDetail> getMembership(
            @RequestHeader(USER_ID_HEADER) final String userId
            , @RequestParam final MembershipType membershipType) {
        return ResponseEntity.ok(membershipService.getMembership(userId, membershipType));
    }

    @DeleteMapping("/api/v1/membership/{id}")
    public ResponseEntity<Void> getMembership (
            @RequestHeader(USER_ID_HEADER) final String userId
            , @PathVariable final Long id) {

        membershipService.removeMembership(id, userId);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/api/v1/membership/{id}/accumulate")
    public ResponseEntity<Void> accumulateMembershipPoint(
            @RequestHeader(USER_ID_HEADER) final String userId
            , @PathVariable final Long id
            , @RequestBody @Validated(ValidationGroups.MembershipAccumulateMarker.class) final MembershipRequest membershipRequest) {

        membershipService.accumulateMembershipPoint(id, userId, membershipRequest.getPoint());
        return ResponseEntity.noContent().build();

    }


}
