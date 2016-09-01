package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminDeviceModel;

import java.util.List;

public interface DeviceModelRepository {

    List<AdminDeviceModel> findAdminDeviceModels();
}
