package com.officeflow.repository;

import com.officeflow.entity.User;
import com.officeflow.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByDepartment(String department);
    boolean existsByEmail(String email);
}
