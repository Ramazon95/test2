package ru.ystu.cmis.service.impl;
import lombok.SneakyThrows;
import ru.ystu.cmis.domain.User;
import ru.ystu.cmis.repository.UserRepository;
import ru.ystu.cmis.service.AuthService;
import ru.ystu.cmis.utill.Self;
import ru.ystu.cmis.utill.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.ArrayList;

public class AuthServiceImpl implements AuthService {
    private ThreadLocal<HttpServletRequest> requestInvoker;
    private ThreadLocal<HttpServletResponse> responseInvoker;
    private final UserRepository repository = ServiceFactory.get(UserRepository.class);

    public Boolean isAdmin() {
        HttpSession session = requestInvoker.get().getSession(true);
        Object isAdmin = session.getAttribute("isAdmin");
        return isAdmin != null && (Boolean) isAdmin;
    }

    @SneakyThrows
    public void redirect(String url){
        responseInvoker.get().sendRedirect(requestInvoker.get().getContextPath()+url);
    }
    @Override
    public void accessRedirect(){if (!isAdmin()) redirect("/user/in"); }

    @Override
    public void exit(){
        HttpSession session = requestInvoker.get().getSession(false);
        if (session!=null) session.invalidate();
    }

    @Override
    public Boolean auth(String login, String password) {
        User user = repository.auth(login, password);
        if(user != null) {
            HttpSession session = requestInvoker.get().getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("isAdmin", user.getRole().equals("admin"));
        }
        /*repository.create(
                User
                        .builder()
                        .login(login)
                        .password(Self.md5(password))
                        .role("admin")
                        .build());*/
        return user != null;
    }
    @Override
    public User getUser(){
        HttpSession session = requestInvoker.get().getSession(true);
        Object user = session.getAttribute("user");
        if (user==null)
            return null;
        return (User) user;
    }
    @Override
    public void createUser(MultivaluedMap<String, String>map){
        HttpSession session = requestInvoker.get().getSession(true);
        User user = new User();
        user.setLogin(map.getFirst("login"));
        user.setPassword(Self.md5(map.getFirst("password")));
        user.setRole("user");
        repository.create(user);
        auth(user.getLogin(),map.getFirst("password"));
    }
    @Override
    public void clearBasket(){
        HttpSession session = requestInvoker.get().getSession(true);
        session.removeAttribute("basket");
    }

    @Override
    public AuthService setParams(ThreadLocal<HttpServletRequest> request, ThreadLocal<HttpServletResponse> response) {
        requestInvoker = request;
        responseInvoker = response;
        System.out.println("setParams");
        return this;
    }
    @Override
    public void addToBasket(Long id){
        HttpSession session = requestInvoker.get().getSession(true);
        Object basketObject = session.getAttribute("basket");
        List<Long> basket = new ArrayList<>();
        if (basketObject !=null){
            basket = (List<Long>) basketObject;
        }
        basket.add(id);
        session.setAttribute("basket", basket );
    }

    @Override
    public List<Long> getBasket(){
        HttpSession session = requestInvoker.get().getSession(true);
        Object basketObject = session.getAttribute("basket");
        if(basketObject !=null){
            return (List<Long>) basketObject;
        }
        return new ArrayList<>();
    }

    @Override
    public void removeFromBasket(Long id){
        HttpSession session = requestInvoker.get().getSession(true);
        List<Long> basket = (List<Long>)session.getAttribute("basket");
        basket.remove(id);
        session.setAttribute("basket",basket);
    }
}
