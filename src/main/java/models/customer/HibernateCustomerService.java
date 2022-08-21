package models.customer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import storage.HibernateUtil;

import java.util.List;

public class HibernateCustomerService implements CustomerService{

    @Override
    public int create(Customer customer) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(customer);
        transaction.commit();
        session.close();
        return customer.getCustomerId();
    }

    @Override
    public Customer getById(int id) {
        try(Session session = openSession()) {
            return session.get(Customer.class, id);
        }
    }

    @Override
    public List<Customer> getAll() {
        try(Session session = openSession()) {
            return session.createQuery("FROM Customer", Customer.class).list();
        }
    }

    @Override
    public String update(Customer customer) {
        try {
            Session session = openSession();
            Transaction transaction = session.beginTransaction();
            session.merge(customer);
            transaction.commit();
            session.close();
            return "true";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String deleteById(int id) {
        try {
            Session session = openSession();
            Transaction transaction = session.beginTransaction();
            session.remove(getById(id));
            transaction.commit();
            session.close();
            return "true";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }
}
