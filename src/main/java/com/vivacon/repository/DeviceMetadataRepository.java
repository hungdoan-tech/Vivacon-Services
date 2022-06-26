package com.vivacon.repository;

import com.vivacon.entity.DeviceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    @Query("select d " +
            "from DeviceMetadata d " +
            "where d.account.id = :accountId and d.location = :location and d.device = :device ")
    Optional<DeviceMetadata> find(Long accountId, String location, String device);
}
