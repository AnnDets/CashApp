package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends ListCrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    UserDetails findUserByUsername(String username);
}