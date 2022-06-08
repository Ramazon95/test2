package ru.ystu.cmis.service;

import ru.ystu.cmis.domain.Event;
import ru.ystu.cmis.dto.Model;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

public interface EventService {
    List<Event> getAll();
    void save(Event event);
    Model save(MultivaluedMap<String,String> params);
    Event get(Long id);
    void delete(Long id);
}
