package io.davlac.user.managment.repository;

import io.davlac.user.managment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
