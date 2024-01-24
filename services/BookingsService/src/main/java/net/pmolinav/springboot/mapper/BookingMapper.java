package net.pmolinav.springboot.mapper;

import net.pmolinav.bookings.dto.BookingDTO;
import net.pmolinav.bookings.model.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking bookingDTOToBookingEntity(BookingDTO bookingDTO);

    BookingDTO bookingEntityTOBookingDTO(Booking booking);

}
