package com.example.tddspringboot.user.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @PrePersist
    private void onPersist() {
        this.createdAt = this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }

}
