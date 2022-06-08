package ru.ystu.cmis.service.impl;

import ru.ystu.cmis.domain.Event;
import ru.ystu.cmis.dto.Model;
import ru.ystu.cmis.repository.EventRepository;
import ru.ystu.cmis.service.EventService;
import ru.ystu.cmis.utill.ServiceFactory;

import javax.ws.rs.core.MultivaluedMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class EventServiceImpl implements EventService {
    private final EventRepository repository = ServiceFactory.get(EventRepository.class);
    @Override
    public List<Event> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Event event) {

        repository.update(event);
    }

    @Override
    public Model save(MultivaluedMap<String, String> params) {
        Event event = new Event();
        event.setName(params.get("name").get(0));
        event.setTickets(Integer.parseInt(params.get("tickets").get(0)));
        event.setTicketFree(event.getTickets());
        event.setPrice(Integer.parseInt(params.get("price").get(0)));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String dtime = params.get("dtime").get(0);
        if(!dtime.isEmpty()){
            event.setDtime(LocalDateTime.parse(dtime,formatter));
        }
        event.setDescription(params.get("description").get(0));
        String id = params.get("id").get(0);
        if(id.isEmpty()){
            repository.create(event);
        } else {
            event.setId(Long.valueOf(id));
            repository.update(event);
        }
        return new Model();
    }

    @Override
    public Event get(Long id) {
        return repository.byId(id);}

    @Override
    public void delete(Long id) {

        repository.delete(id);
    }

}
