package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends ListCrudRepository<Account, UUID> {
    List<Account> findByUserId(UUID userId);

    @Modifying
    @Query("update Account a set a.defaultAccount = false where a.user.id = :userId")
    void setDefaultMark(@Param("userId") UUID userId);
}