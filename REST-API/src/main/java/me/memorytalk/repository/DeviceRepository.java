package me.memorytalk.repository;

import me.memorytalk.common.code.DeviceOs;
import me.memorytalk.domain.Device;
import me.memorytalk.repository.custom.DeviceModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>,
        JpaSpecificationExecutor<Device>,
        DeviceModelRepository {

    Long countByOs(DeviceOs os);

    Device findByOsAndToken(DeviceOs os, String token);
}
