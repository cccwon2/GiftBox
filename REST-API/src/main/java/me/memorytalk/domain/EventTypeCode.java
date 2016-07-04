package me.memorytalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "eventTypeCodes")
public class EventTypeCode implements Serializable {

    private static final long serialVersionUID = 1943580678513277015L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String sort;

    @Column(nullable = false)
    private Date createdDate;

    @OneToMany(mappedBy="eventTypeCode", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<EventType> eventTypes;
}
