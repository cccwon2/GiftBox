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
@Table(name = "events")
public class Event implements Serializable {

    private static final long serialVersionUID = -3210385684140527959L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String homePage;

    @Column(nullable = false)
    private String eventPage;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private Date publicationDate;

    @Column(nullable = false)
    private Date registrationDate;

    @Column(nullable = false)
    private boolean premium;

    @Column(nullable = false)
    private boolean visible;

    @Column(nullable = false)
    private Date createdDate;

    @Column(nullable = false)
    private Date updatedDate;

    @OneToMany(mappedBy="event", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<Gift> gifts;

    @OneToMany(mappedBy="event", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<Tag> tags;

    @OneToMany(mappedBy="event", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<Thumbnail> thumbnails;
}
