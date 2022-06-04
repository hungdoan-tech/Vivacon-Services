package com.vivacon.repository;

import com.vivacon.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Long> {

    @Query("select us.value from Setting us where us.setting.name = :type and us.account.id = :accountId")
    Optional<String> findValueByAccountIdAndSettingType(@Param("accountId") long accountId, @Param("type") String type);

    @Query("select s.defaultValue from Setting s where s.name = :type")
    Optional<String> findDefaultValueBySettingType(@Param("type") String type);
}
