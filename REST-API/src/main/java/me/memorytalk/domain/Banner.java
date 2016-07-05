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
@Table(name = "banners")
public class Banner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long eventId;

    @Column
    private String bannerPage;

    @Column(nullable = false)
    private boolean visible;

    @Column(nullable = false)
    private Date createdDate;

    @OneToMany(mappedBy="banner", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<Attachment> attachments;
}
