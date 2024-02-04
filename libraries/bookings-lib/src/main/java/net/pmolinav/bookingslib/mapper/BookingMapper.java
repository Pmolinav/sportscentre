package net.pmolinav.bookingslib.mapper;

import net.pmolinav.bookingslib.dto.BookingDTO;
import net.pmolinav.bookingslib.model.Booking;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking bookingDTOToBookingEntity(BookingDTO bookingDTO);

    BookingDTO bookingEntityTOBookingDTO(Booking booking);

}
