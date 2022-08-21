package models.project;

import java.util.List;

public interface ProjectService {
    int create(Project project, int companyId, int customerId);
    Project getById(int id);
    List<Project> getAll();
    String update(Project project, int companyId, int customerId);
    String deleteById(int id);
}
