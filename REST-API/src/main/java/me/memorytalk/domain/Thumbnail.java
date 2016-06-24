package me.memorytalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "thumbnails")
public class Thumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String small;

    @Column(nullable = false)
    private String medium;

    @Column(nullable = false)
    private String large;

    @ManyToOne
    @JoinColumn(name = "event", nullable = true)
    private Event event;
}
