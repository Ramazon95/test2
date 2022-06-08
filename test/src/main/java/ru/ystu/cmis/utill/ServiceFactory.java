package ru.ystu.cmis.utill;

import ru.ystu.cmis.repository.EventRepository;
import ru.ystu.cmis.repository.TicketRepository;
import ru.ystu.cmis.repository.TokenRepository;
import ru.ystu.cmis.repository.UserRepository;
import ru.ystu.cmis.service.AuthService;
import ru.ystu.cmis.service.EventService;
import ru.ystu.cmis.service.TicketService;
import ru.ystu.cmis.service.impl.AuthServiceImpl;
import ru.ystu.cmis.service.impl.EventServiceImpl;
import ru.ystu.cmis.service.impl.TicketServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private static final Map<Class<?>,Object> services = new HashMap<>(5);
    private static final ServiceFactory factory;
    static {
        services.put(EventRepository.class,new EventRepository());
        services.put(TicketRepository.class,new TicketRepository());
        services.put(TokenRepository.class,new TokenRepository());
        services.put(UserRepository.class,new UserRepository());

        services.put(TicketService.class,new TicketServiceImpl());
        services.put(EventService.class,new EventServiceImpl());
        services.put(AuthService.class,new AuthServiceImpl());
        factory = new ServiceFactory();
        System.out.println("init ServiceFactory");
    }
    public static ServiceFactory getInstance(){
        return factory;
    }
    public void check(){

    }
    public static <T> T get(Class<T> type){return (T) services.get(type);}

}
