package net.pmolinav.bookingslib.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ActivityDTO {
    private ActivityType type;
    private String name;
    private String description;
    private BigDecimal price;
    private Date creationDate;
    private Date modificationDate;

}
