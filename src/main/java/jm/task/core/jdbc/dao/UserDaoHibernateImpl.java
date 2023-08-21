package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = getSessionFactory();

    @Override
    public void createUsersTable() {
        SessionFactory sessionFactoryToCreate = Util.getSessionFactory();
        Session session = sessionFactoryToCreate.getCurrentSession();
        try (sessionFactoryToCreate;
             session) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user" +
                    "(" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(45) ," +
                    "lastname VARCHAR(45) ," +
                    "age TINYINT(10) " +
                    ")").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }catch (IllegalStateException e){
            session.getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactoryToDrop = Util.getSessionFactory();
        Session session = sessionFactoryToDrop.getCurrentSession();
        try (sessionFactoryToDrop;
             session) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            session.getTransaction().commit();
        }catch (IllegalStateException e){
            session.getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactoryToRemove = Util.getSessionFactory();
        Session session = sessionFactoryToRemove.getCurrentSession();
        try (sessionFactoryToRemove;
             session) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = :id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        }catch (IllegalStateException e){
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {

        try (SessionFactory sessionFactoryToGet = Util.getSessionFactory();
             Session session = sessionFactoryToGet.getCurrentSession()) {
            session.beginTransaction();
            return session.createQuery("FROM User", User.class).list();
        }
    }
    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactoryToClean = Util.getSessionFactory();
        Session session = sessionFactoryToClean.getCurrentSession();
        try (sessionFactoryToClean;
             session) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }catch (IllegalStateException e){
            session.getTransaction().rollback();
        }
    }
}