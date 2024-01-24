package net.pmolinav.springboot.mapper;

import net.pmolinav.springboot.dto.BookingDTO;
import net.pmolinav.springboot.model.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking bookingDTOToBookingEntity(BookingDTO bookingDTO);

    BookingDTO bookingEntityTOBookingDTO(Booking booking);

}
