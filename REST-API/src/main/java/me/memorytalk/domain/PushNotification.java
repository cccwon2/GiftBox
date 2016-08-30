package me.memorytalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "pushNotifications")
public class PushNotification implements Serializable {

    private static final long serialVersionUID = 4560071234250453548L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;

    @Column(nullable = false)
    private Date createdDate;
}
