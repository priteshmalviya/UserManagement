package com.pritesh.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pritesh.usermanagement.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    User findByEmail(String email);

}
