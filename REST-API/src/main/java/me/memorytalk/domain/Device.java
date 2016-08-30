package me.memorytalk.domain;

import me.memorytalk.common.code.DeviceOs;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "devices")
public class Device implements Serializable {

    private static final long serialVersionUID = -1214928650940204589L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private DeviceOs os;

    @Column(nullable=false, length=255)
    private String token;

    @Column(nullable = false)
    private Date createdDate;
}
