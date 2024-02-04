package net.pmolinav.bookings.mapper;

import net.pmolinav.bookingslib.dto.ActivityDTO;
import net.pmolinav.bookingslib.model.Activity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    Activity activityDTOToActivityEntity(ActivityDTO activityDTO);

    ActivityDTO activityEntityTOActivityDTO(Activity activity);

}
