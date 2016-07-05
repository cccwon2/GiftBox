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
@Table(name = "popups")
public class Popup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long eventId;

    @Column
    private String popupPage;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private boolean visible;

    @Column(nullable = false)
    private Date createdDate;

    @OneToMany(mappedBy="popup", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<Attachment> attachments;
}
