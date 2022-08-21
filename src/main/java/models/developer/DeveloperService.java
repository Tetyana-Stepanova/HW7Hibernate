package models.developer;

import java.util.List;

public interface DeveloperService {
    int create(Developer developer, int[] skillIds, int companyId, int[] projectIds);
    Developer getById(int id);
    List<Developer> getAll();
    String update(Developer developer, int[] skillIds, int companyId, int[] projectIds);
    String deleteById(int id);
    List<Developer> getByDevLanguage(String devLanguage);
    List<Developer> getBySkillLevel(String skillLevel);
    List<Developer> getDevByProjectId(int projectId);
}
