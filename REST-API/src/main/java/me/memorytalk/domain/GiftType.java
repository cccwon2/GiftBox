package me.memorytalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "giftTypes")
public class GiftType implements Serializable {

    private static final long serialVersionUID = 3669654641017048175L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "event", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "giftTypeCode", nullable = false)
    private GiftTypeCode giftTypeCode;
}
