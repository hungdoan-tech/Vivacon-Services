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

    @Query("Select a from Account a " +
            "where " +
            "a.username like CONCAT('%',:keyword,'%') or a.fullName like CONCAT('%',:keyword,'%')")
    Page<Account> findByApproximatelyName(@Param("keyword") String keyword, Pageable pageable);

    @Query("select count(a.id) from Account a where a.active = true")
    Long getAllAccountCounting();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.active = false WHERE a.id = :id")
    int deactivateById(@Param("id") Long id);

    @Query("SELECT a from Account a where a.role.id = :roleId and a.active = :active")
    Page<Account> findByRoleIdAndActive(@Param("roleId") long roleId, @Param("active") boolean active, Pageable pageable);
}
