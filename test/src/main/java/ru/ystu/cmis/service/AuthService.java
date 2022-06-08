package ru.ystu.cmis.service;

import ru.ystu.cmis.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

public interface AuthService {
    Boolean isAdmin();
    void redirect(String url);
    void accessRedirect();

    void exit();

    Boolean auth(String login, String password);

    void clearBasket();

    AuthService setParams(ThreadLocal<HttpServletRequest> request, ThreadLocal<HttpServletResponse> response);

    User getUser();

    void createUser(MultivaluedMap<String, String> map);

    void addToBasket(Long id);

    List<Long> getBasket();

    void removeFromBasket(Long id);
}
