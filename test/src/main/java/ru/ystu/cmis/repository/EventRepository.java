package ru.ystu.cmis.repository;

import org.hibernate.Session;
import ru.ystu.cmis.domain.Event;

import java.util.List;

public class EventRepository extends Repository<Event> {
    public List<Event> getAll(){
        Session sess = sf.openSession();
        List<Event> list = sess.createQuery("SELECT e FROM Event e", Event.class)
                .getResultList();
        sess.close();
        return list;
    }
}
