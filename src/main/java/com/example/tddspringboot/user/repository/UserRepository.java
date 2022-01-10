package com.example.tddspringboot.user.repository;

import com.example.tddspringboot.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
