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
@Table(name = "attachments")
public class Attachment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private int sort;

    @Column(nullable = false)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "event", nullable = true)
    private Event event;

    @OneToMany(mappedBy="attachment", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<Thumbnail> thumbnails;
}
