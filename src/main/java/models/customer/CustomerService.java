package models.customer;

import java.util.List;

public interface CustomerService {
    int create(Customer customer);
    Customer getById(int id);
    List<Customer> getAll();
    String update(Customer customer);
    String deleteById(int id);
}
