package ru.ystu.cmis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ystu.cmis.domain.Event;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    public EventDto(Event event){
        id = event.getId();
        description = event.getDescription();
        dtime = event.getDtime();
        name = event.getName();
        tickets = event.getTickets();
        ticketFree = event.getTicketFree();
        price = event.getPrice();
        count = event.getCount();
    }
    private Long id;
    private LocalDateTime dtime;
    private String name;
    private Integer tickets;
    private Integer ticketFree;
    private String description;
    private Integer price;
    private Integer count;
}
