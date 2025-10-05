package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDao implements IUserDao {

    public UserDao(){
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            session.isConnected();
        }
    }

    @Override
    public List<User> find() {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);

            Root<User> userRoot = criteriaQuery.from(User.class);
            criteriaQuery.select(userRoot);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public User findById(int id) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            return session.find(User.class, id);
        }
    }

    @Override
    public void save(User user) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        }
    }

    @Override
    public void update(User user) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
        }
    }

    @Override
    public void delete(User user) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.remove(user);
            tx.commit();
        }
    }
}
