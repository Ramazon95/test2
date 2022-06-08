package ru.ystu.cmis.controller;
import com.sun.jersey.api.view.Viewable;
import jdk.jfr.internal.consumer.RecordingInput;
import lombok.ToString;
import ru.ystu.cmis.config.WebType;
import ru.ystu.cmis.domain.Event;
import ru.ystu.cmis.dto.EventDto;
import ru.ystu.cmis.dto.Model;
import ru.ystu.cmis.service.AuthService;
import ru.ystu.cmis.service.EventService;
import ru.ystu.cmis.utill.Self;
import ru.ystu.cmis.utill.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/")
public class Index {

    @Context
    ThreadLocal<HttpServletRequest> requestInvoker;
    @Context
    ThreadLocal<HttpServletResponse> responseInvoker;

    private final EventService eventService = ServiceFactory.get(EventService.class);
    private final AuthService authService = ServiceFactory.get(AuthService.class);

    @GET
    @Path("item/{id}")
    @Produces(WebType.TEXT_HTML)
    public Viewable item(@PathParam("id") Long id) {
        Model model = new Model();
        model.put("event", eventService.get(id));
        model.put("title","Карточка мероприятия");
        return Self.view("item", model);
    }
    @GET
    @Produces(WebType.TEXT_HTML)
    public Viewable index() {
        Model model = new Model();
        model.put("list", eventService.getAll());
        model.put("title","Список товаров");
        return Self.view("index", model);
    }

    @GET
    @Path("addToBasket/{id}")
    @Produces(WebType.TEXT_HTML)
    public Viewable addToBasket(@PathParam("id") Long id) {
        authService.setParams(requestInvoker, responseInvoker);
        authService.addToBasket(id);
        authService.redirect("/basket");
        return null;
    }

    @GET
    @Path("removeFromBasket/{id}")
    @Produces(WebType.TEXT_HTML)
    public Viewable removeFromBasket(@PathParam("id") Long id) {
        authService.setParams(requestInvoker, responseInvoker);
        authService.removeFromBasket(id);
        authService.redirect("/basket");
        return null;
    }

    @GET
    @Path("clearBasket")
    @Produces(WebType.TEXT_HTML)
    public Viewable clearBasket(){
        authService.setParams(requestInvoker, responseInvoker);
        authService.clearBasket();
        authService.redirect("/");
        return null;
    }

    @GET
    @Path("basket")
    @Produces(WebType.TEXT_HTML)
    public Viewable basketList(){
        List<Long> basket = authService.setParams(requestInvoker, responseInvoker).getBasket();
        List<Event> events = eventService.getAll();
        List<EventDto> basketList = new ArrayList<>();
        for (Event event: events){
            if (basket.contains(event.getId())){
                int count = 0;
                for (Long id : basket){
                    if (id.equals(event.getId())) count++;
                }
                EventDto dto = new EventDto(event);
                dto.setCount(count);
                basketList.add(dto);
            }
        }
        Model model = new Model();
        model.put("list", basketList);
        return Self.view("basket", model);
    }
}
