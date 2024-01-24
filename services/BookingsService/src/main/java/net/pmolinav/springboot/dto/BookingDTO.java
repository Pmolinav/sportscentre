package net.pmolinav.springboot.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BookingDTO {
    private long userId;
    private long activityId;
    private Date startTime;
    private Date endTime;
    private BookingStatus status;

}
