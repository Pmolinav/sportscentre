package net.pmolinav.bookings.mapper;

import net.pmolinav.bookings.dto.ActivityDTO;
import net.pmolinav.bookings.model.Activity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    Activity activityDTOToActivityEntity(ActivityDTO activityDTO);
    ActivityDTO activityEntityTOActivityDTO(Activity activity);

}
