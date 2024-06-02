package net.pmolinav.bookingslib.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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

    @Min(value = 1, message = "Activity price is mandatory and must be an integer greater than zero.")
    private Integer price;

}
