package me.memorytalk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.memorytalk.common.code.DeviceOs;
import me.memorytalk.common.json.DateSerializer;

import java.util.Date;

@Data
public class AdminDeviceModel {

    private Long id;

    private DeviceOs os;

    private String token;

    @JsonSerialize(using = DateSerializer.class)
    private Date createdDate;

    public AdminDeviceModel(Long id, DeviceOs os, String token, Date createdDate) {
        this.id = id;
        this.os = os;
        this.token = token;
        this.createdDate = createdDate;
    }
}
