package models.skill;

import java.util.List;

public interface SkillService {
    int create(Skill skill);
    Skill getById(int id);
    List<Skill> getAll();
    String update(Skill skill);
    String deleteById(int id);
}
