package net.pmolinav.bookings.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private long bookingId;

    @Column(name = "userId", nullable = false)
    private long userId;

    @Column(name = "activityId", nullable = false)
    private long activityId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startTime", nullable = false)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endTime", nullable = false)
    private Date endTime;

    @Column(name = "status", nullable = false)
    private String status = "OPEN";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modificationDate")
    private Date modificationDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Activity activity;

    public Booking(long bookingId, long userId, long activityId, Date startTime, Date endTime, String status, Date creationDate, Date modificationDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.activityId = activityId;
        this.startTime = startTime;
        this.endTime = endTime;
        if (status != null) {
            this.status = status;
        }
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }
}
