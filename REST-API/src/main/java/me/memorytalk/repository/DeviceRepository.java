package me.memorytalk.repository;

import me.memorytalk.common.code.DeviceOs;
import me.memorytalk.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Long countByOs(DeviceOs os);

    Device findByOsAndToken(DeviceOs os, String token);
}
