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
@Table(name = "giftTypeCodes")
public class GiftTypeCode implements Serializable {

    private static final long serialVersionUID = 2055697524079477374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date createdDate;

    @OneToMany(mappedBy="giftTypeCode", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<GiftType> giftTypes;
}
