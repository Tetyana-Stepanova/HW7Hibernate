package models.company;

import org.hibernate.Session;
import org.hibernate.Transaction;
import storage.HibernateUtil;

import java.util.List;

public class HibernateCompanyService implements CompanyService{

    @Override
    public int create(Company company) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(company);
        transaction.commit();
        session.close();
        return company.getCompanyId();
    }

    @Override
    public Company getById(int id) {
        try(Session session = openSession()) {
            return session.get(Company.class, id);
        }
    }

    @Override
    public List<Company> getAll() {
        try(Session session = openSession()) {
            return session.createQuery("from Company", Company.class).list();
        }
    }

    @Override
    public String update(Company company) {
        try {
            Session session = openSession();
            Transaction transaction = session.beginTransaction();
            session.merge(company);
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
