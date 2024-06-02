package net.pmolinav.bookingslib.dto;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BookingDTO {
    @Positive(message = "User ID must be positive.")
    private long userId;

    @Positive(message = "Activity ID must be positive.")
    private long activityId;

    @NotNull(message = "Start time is mandatory.")
    @Future(message = "Start time must be in the future.")
    private Date startTime;

    @NotNull(message = "End time is mandatory.")
    @Future(message = "End time must be in the future.")
    private Date endTime;

    @NotNull(message = "Booking status is mandatory.")
    private BookingStatus status;

}
