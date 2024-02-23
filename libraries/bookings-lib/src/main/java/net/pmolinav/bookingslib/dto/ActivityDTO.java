package net.pmolinav.bookingslib.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ActivityDTO {

    @NotNull(message = "Activity type is mandatory.")
    private ActivityType type;

    @NotBlank(message = "Activity name is mandatory.")
    private String name;

    private String description;

    @DecimalMin(value = "0.01", message = "Activity price is mandatory and must be greater than zero.")
    private BigDecimal price;

    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") // Date format
    private Date creationDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") // Date format
    private Date modificationDate;

}
