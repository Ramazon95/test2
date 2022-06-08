package ru.ystu.cmis.repository;

import lombok.SneakyThrows;
import org.hibernate.Session;
import ru.ystu.cmis.domain.User;
import ru.ystu.cmis.utill.Self;

import java.util.List;

public class UserRepository extends Repository<User>{

    @SneakyThrows
    public User auth(String login, String password){
        String pass = Self.md5(password);
        Session sess = sf.openSession();
        List<User> users = sess.createQuery("SELECT u FROM User u WHERE" +
                        " u.login = :login and u.password = :password", User.class)
                .setParameter("login", login)
                .setParameter("password", pass)
                .getResultList();
        sess.close();
        if (users != null && users.size() > 0)
            return users.get(0);
        return null;
    }
}
