package ru.ystu.cmis.controller;

import com.sun.jersey.api.view.Viewable;
import ru.ystu.cmis.config.WebType;
import ru.ystu.cmis.domain.User;
import ru.ystu.cmis.dto.Model;
import ru.ystu.cmis.service.AuthService;
import ru.ystu.cmis.utill.Self;
import ru.ystu.cmis.utill.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

@Path("/user")
public class UserController {
    private final AuthService authService = ServiceFactory.get(AuthService.class);

    @Context
    ServletContext servletContext;

    @Context
    ThreadLocal<HttpServletRequest> requestInvoker;

    @Context
    ThreadLocal<HttpServletResponse> responseInvoker;

    @GET
    @Produces(WebType.TEXT_HTML)
    public Viewable info(){
        User user = authService.setParams(requestInvoker, responseInvoker).getUser();
    if(user == null){
        authService.redirect("/user/in");
    }
    Model model = new Model();
    model.put("user",model);
    authService.setParams(requestInvoker, responseInvoker)
            .redirect("/basket");
    return null;
    }

    @POST
    @Path("/userIn")
    @Produces(WebType.TEXT_HTML)
    public Viewable userIn(MultivaluedMap<String, String>map){
        if (authService.auth(map.getFirst("login"),
                map.getFirst("password"))){
            authService.setParams(requestInvoker, responseInvoker)
                    .redirect("/");
            return null;
        }
        authService.redirect("/userIn");
        return null;
    }

    @GET
    @Path("/registration")
    @Produces(WebType.TEXT_HTML)
    public Viewable registrationPage(){
        return Self.view("registration");
    }

    @POST
    @Path("/registration")
    @Produces(WebType.TEXT_HTML)
    public Viewable registrationIn(MultivaluedMap<String, String>map){
        authService.setParams(requestInvoker, responseInvoker);
        if (authService.auth(map.getFirst("login"),
                map.getFirst("password"))){
            authService.setParams(requestInvoker, responseInvoker)
                    .redirect("/user/in");
            return null;
        } //Проверяем, не является ли регистрирующийся уже юзером
        authService.setParams(requestInvoker, responseInvoker).createUser(map);
        authService.redirect("/");
        return null;
    }//... иначе регистрируем нового юзера и редиректим в корзину

    @GET
    @Path("/in")
    @Produces(WebType.TEXT_HTML)
    public Viewable authPage(){
        return Self.view("userIn");
    }


    @GET
    @Path("/exit")
    public void exitPage(){
        authService.setParams(requestInvoker, responseInvoker).exit();
        authService.redirect("/");
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")//???????????????????????????????????
    @Produces(WebType.TEXT_HTML)
    public void checkAuth(MultivaluedMap<String, String> auth){
        authService.setParams(requestInvoker, responseInvoker);
        if (!authService.auth(auth.getFirst("login"),
                auth.getFirst("password"))){
            authService
                    .redirect("/user/in");
            return;
        }//Проверяем является ли входящий юзером
        if(authService.isAdmin()){
            authService
                    .redirect("/admin");
            return;
        }//... если он админ


        authService
                .redirect("/basket");
    }//... иначе редирект в корзину

}
