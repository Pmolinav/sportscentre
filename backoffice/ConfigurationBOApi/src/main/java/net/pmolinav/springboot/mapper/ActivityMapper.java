package net.pmolinav.springboot.mapper;

import net.pmolinav.springboot.dto.ActivityDTO;
import net.pmolinav.springboot.model.Activity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    Activity activityDTOToActivityEntity(ActivityDTO activityDTO);
    ActivityDTO activityEntityTOActivityDTO(Activity activity);

}
