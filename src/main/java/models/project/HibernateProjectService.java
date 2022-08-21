package models.project;

import models.company.Company;
import models.customer.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import storage.HibernateUtil;

import java.util.List;

public class HibernateProjectService implements ProjectService{
    @Override
    public int create(Project project, int companyId, int customerId) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        Company company = session.get(Company.class, companyId);
        project.setCompany(company);
        Customer customer = session.get(Customer.class, customerId);
        project.setCustomer(customer);
        session.persist(project);
        transaction.commit();
        session.close();
        return project.getProjectId();
    }

    @Override
    public Project getById(int id) {
        try(Session session = openSession()) {
            Project project = session.get(Project.class, id);
            project.getCompany();
            project.getCustomer();
            return project;
        }
    }

    @Override
    public List<Project> getAll() {
        try(Session session = openSession()) {
            List<Project> projects = session.createQuery("from Project", Project.class).list();
            for(Project project : projects){
                project.getCompany();
                project.getCustomer();
            }
            return projects;
        }
    }

    @Override
    public String update(Project project, int companyId, int customerId) {
        try {
            Session session = openSession();
            Transaction transaction = session.beginTransaction();
            Company company = session.get(Company.class, companyId);
            project.setCompany(company);
            Customer customer = session.get(Customer.class, customerId);
            project.setCustomer(customer);
            session.merge(project);
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
