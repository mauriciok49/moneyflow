package com.mauricio.moneyflow.repository;

import com.mauricio.moneyflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
