package models.developer;

import jakarta.transaction.Transactional;
import models.company.Company;
import models.project.Project;
import models.skill.Skill;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import storage.HibernateUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
public class HibernateDeveloperService implements DeveloperService{
    @Override
    public int create(Developer developer, int[] skillIds, int companyId, int[] projectIds) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        Company company = session.get(Company.class, companyId);
        developer.setCompany(company);

        Set<Skill> skills = new HashSet<>();
        for (int skillId : skillIds) {
            skills.add(session.get(Skill.class, skillId));
        }
        developer.setSkills(skills);

        Set<Project> projects = new HashSet<>();
        for (int projectId : projectIds){
            projects.add(session.get(Project.class, projectId));
        }
        developer.setProjects(projects);

        session.persist(developer);
        transaction.commit();
        session.close();
        return developer.getDeveloperId();
    }

    @Override
    public Developer getById(int id) {
        try (Session session = openSession()) {
            Developer developer = session.get(Developer.class, id);
            developer.getCompany();
            for (Skill skill : developer.getSkills()) {}
            for (Project project : developer.getProjects()){}
            return developer;
        }
    }

    @Override
    public List<Developer> getAll() {
        try(Session session = openSession()) {
            NativeQuery<Developer> nativeQuery = session.createNativeQuery("Select * from developer", Developer.class);
            List<Developer> developers = nativeQuery.list();
            for (Developer developer : developers) {
                developer.getCompany();
                for (Skill skill : developer.getSkills()) {}
                for (Project project : developer.getProjects()){}
            }
           return developers;
        }
    }

    @Override
    public String update(Developer developer, int[] skillIds, int companyId, int[] projectIds) {
        try {
            Session session = openSession();
            Transaction transaction = session.beginTransaction();

            Company company = session.get(Company.class, companyId);
            developer.setCompany(company);

            Set<Skill> skills = new HashSet<>();
            for (int skillId : skillIds) {
                skills.add(session.get(Skill.class, skillId));
            }
            developer.setSkills(skills);
            Set<Project> projects = new HashSet<>();
            for (int projectId : projectIds){
                projects.add(session.get(Project.class, projectId));
            }
            developer.setProjects(projects);

            session.merge(developer);
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

    @Override
    public List<Developer> getByDevLanguage(String devLanguage) {
        try(Session session = openSession()) {
            NativeQuery<Developer> nativeQuery = session.createNativeQuery(
                    "SELECT developer.*" +
                            "FROM developer " +
                            "LEFT JOIN developers_skills " +
                            "ON developers_skills.developer_id = developer.developer_id " +
                            "LEFT JOIN skill " +
                            "ON developers_skills.skill_id = skill.skill_id " +
                            "WHERE skill.dev_language = :devLanguage",
                    Developer.class
            );
            nativeQuery.setParameter("devLanguage", devLanguage);
            List<Developer> developers = nativeQuery.list();
            for (Developer developer : developers) {
                developer.getCompany();
                for (Skill skill : developer.getSkills()) {}
                for (Project project : developer.getProjects()){}
            }
            return developers;
        }
    }

    @Override
    public List<Developer> getBySkillLevel(String skillLevel) {
        try(Session session = openSession()) {
            NativeQuery<Developer> nativeQuery = session.createNativeQuery(
                    "SELECT developer.*" +
                            "FROM developer " +
                            "LEFT JOIN developers_skills " +
                            "ON developers_skills.developer_id = developer.developer_id " +
                            "LEFT JOIN skill " +
                            "ON developers_skills.skill_id = skill.skill_id " +
                            "GROUP BY skill.skill_level, developer.developer_id " +
                            "HAVING skill_level = :skillLevel",
                    Developer.class
            );
            nativeQuery.setParameter("skillLevel", skillLevel);
            List<Developer> developers = nativeQuery.list();
            for (Developer developer : developers) {
                developer.getCompany();
                for (Skill skill : developer.getSkills()) {}
                for (Project project : developer.getProjects()){}
            }
            return developers;
        }
    }

    @Override
    public List<Developer> getDevByProjectId(int projectId) {
        try(Session session = openSession()) {
            NativeQuery<Developer> nativeQuery = session.createNativeQuery(
                    "SELECT developer.* " +
                            "FROM developers_projects " +
                            "LEFT JOIN developer " +
                            "ON developer.developer_id = developers_projects.developer_id " +
                            "WHERE project_id = :projectId",
                    Developer.class
            );
            nativeQuery.setParameter("projectId", projectId);
            List<Developer> developers = nativeQuery.list();
            for (Developer developer : developers) {
                developer.getCompany();
                for (Skill skill : developer.getSkills()) {}
                for (Project project : developer.getProjects()){}
            }
            return developers;
        }
    }

    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }
}
