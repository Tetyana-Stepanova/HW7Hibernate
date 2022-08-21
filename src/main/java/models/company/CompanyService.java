package models.company;

import java.util.List;

public interface CompanyService {
    int create(Company company);
    Company getById(int id);
    List<Company> getAll();
    String update(Company company);
    String deleteById(int id);
}
