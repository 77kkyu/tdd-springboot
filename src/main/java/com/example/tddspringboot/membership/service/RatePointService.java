package com.example.tddspringboot.membership.service;

import com.example.tddspringboot.membership.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatePointService implements PointService {

    private static final int POINT_RATE = 1;

    @Override
    public int calculateAmount(final int price) {
        return price * POINT_RATE / 100;
    }



}
