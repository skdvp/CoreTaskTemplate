package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {

    private final SessionFactory factory = getFactory();
    private static final String SELECT = "SELECT * FROM users";
    private static final String DROP = "DROP TABLE users;";
    private static final String TRUNC = "TRUNCATE TABLE users;";
    private static final String CREATE = "CREATE TABLE users (" +
            " id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
            " name VARCHAR(20) NOT NULL," +
            " lastName VARCHAR(20) NOT NULL," +
            " age INT NOT NULL);";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery(CREATE).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("До этого была таблица");
            e.printStackTrace();
            try {
                session.beginTransaction();
                session.createSQLQuery(TRUNC).executeUpdate();
                session.getTransaction().commit();
            } finally {
                session.close();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery(DROP).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("Таблицы не существует");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + "  добавлен в базу данных" + "\n");
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Некорректный ввод данных");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(SELECT).addEntity(User.class);
            userList = query.list();
            for (User user : userList) {
                System.out.println(user);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery(TRUNC).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
