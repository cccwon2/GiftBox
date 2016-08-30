package me.memorytalk.service;

import me.memorytalk.common.code.DeviceOs;
import me.memorytalk.domain.Device;
import me.memorytalk.dto.DeviceForm;
import me.memorytalk.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Boolean addDevice(DeviceForm requestForm) {

        DeviceOs OS = requestForm.getOs();
        String token = requestForm.getToken();
        Assert.notNull(OS, "No Device OS.");

        if(OS == DeviceOs.ANDROID) {
            Assert.hasText(token, "NO gcm registration id.");
        } else {
            Assert.hasText(token, "No token id.");
        }

        Device device = new Device();
        device.setOs(OS);
        device.setToken(token);
        device.setCreatedDate(new Date());

        deviceRepository.save(device);

        return Boolean.TRUE;
    }

    public Boolean removeDevice(DeviceForm requestForm) {

        Device device = deviceRepository.findByOsAndToken(requestForm.getOs(), requestForm.getToken());
        if(device != null) {
            deviceRepository.delete(device);
        }

        return Boolean.TRUE;
    }

    public Long getAdminAndroidCount() {

        return deviceRepository.countByOs(DeviceOs.ANDROID);
    }

    public Long getAdminIosCount() {

        return deviceRepository.countByOs(DeviceOs.IOS);
    }
}
