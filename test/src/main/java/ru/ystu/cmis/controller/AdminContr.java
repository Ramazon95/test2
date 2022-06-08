package ru.ystu.cmis.controller;

import com.sun.jersey.api.view.Viewable;
import ru.ystu.cmis.config.WebType;
import ru.ystu.cmis.domain.Event;
import ru.ystu.cmis.dto.Model;
import ru.ystu.cmis.service.AuthService;
import ru.ystu.cmis.service.EventService;
import ru.ystu.cmis.utill.Self;
import ru.ystu.cmis.utill.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
@Path("/admin")
public class AdminContr {
    @Context
    ThreadLocal<HttpServletRequest> requestInvoker;
    @Context
    ThreadLocal<HttpServletResponse> responseInvoker;

    private final EventService eventService = ServiceFactory.get(EventService.class);
    private final AuthService authService = ServiceFactory.get(AuthService.class);
    @GET
    @Produces(WebType.TEXT_HTML)
    public Viewable index() {
        authService.setParams(requestInvoker, responseInvoker).accessRedirect();
        Model model = new Model();
        model.put("title","Администрирование");
        model.put("list",eventService.getAll());
        return Self.view("admin", model);
    }

    @Path("/add")
    @GET
    @Produces(WebType.TEXT_HTML)
    public Viewable add() {
        Model model = new Model();
        model.put("title","Добавление мероприятия");
        model.put("controlName","Добавить мероприятия");
        model.put("event",new Event());
        return Self.view("add", model);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(WebType.TEXT_HTML)
    public Viewable save(MultivaluedMap<String, String> event) {
        eventService.save(event);
        return index();
    }

    @GET
    @Path("/remove/{id}")
    @Produces(WebType.TEXT_HTML)
    public Viewable remove(@PathParam("id") Long id) {
        eventService.delete(id);
        return index();
    }

    @GET
    @Path("/edit/{id}")
    @Produces(WebType.TEXT_HTML)
    public Viewable edit(@PathParam("id") Long id) {
        Model model = new Model();
        model.put("event",eventService.get(id));
        model.put("title","Изменение мероприятия");
        model.put("controlName","Изменить мероприятия");
        return Self.view("add", model);
    }
}
