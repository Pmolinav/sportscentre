package net.pmolinav.configuration.mapper;

import net.pmolinav.bookingslib.dto.BookingDTO;
import net.pmolinav.bookingslib.model.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking bookingDTOToBookingEntity(BookingDTO bookingDTO);

    BookingDTO bookingEntityTOBookingDTO(Booking booking);

}
