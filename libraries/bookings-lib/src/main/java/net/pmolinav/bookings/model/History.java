package net.pmolinav.bookings.model;

import lombok.*;
import javax.persistence.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "activities")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    @Column(name = "changeType", nullable = false)
    private String changeType;

    @Column(name = "originalEntity", nullable = false)
    private String originalEntity;

    @Column(name = "finalEntity")
    private String finalEntity;

}
