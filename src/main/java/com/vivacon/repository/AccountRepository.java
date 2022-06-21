package com.vivacon.repository;

import com.vivacon.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT account FROM Account account WHERE account.id = :id and account.active = :isActive")
    Optional<Account> findByIdAndActive(@Param(value = "id") long id, @Param(value = "isActive") boolean isActive);

    Optional<Account> findByUsernameIgnoreCase(String username);

    Optional<Account> findByRefreshToken(String token);

    Page<Account> findAll(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Account a set a.refreshToken = null, a.tokenExpiredDate = null where a.username = :username")
    int setRefreshTokenToEmptyByUsername(@Param("username") String username);

    Optional<Account> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update Account a set a.active = true, a.verificationToken = null, a.verificationExpiredDate = null where a.verificationToken = :token")
    int activateByVerificationToken(@Param("token") String token);

    Optional<Account> findByVerificationToken(String token);

    @Query("select count(a.id) from Account a where a.active = true")
    Long getAllAccountCounting();
}
