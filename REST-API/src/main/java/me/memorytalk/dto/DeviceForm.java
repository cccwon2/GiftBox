package me.memorytalk.dto;

import me.memorytalk.common.code.DeviceOs;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceForm {

    private DeviceOs os;

    private String token;
}
