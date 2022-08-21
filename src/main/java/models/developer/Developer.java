package models.developer;

import jakarta.persistence.*;
import lombok.Data;
import models.company.Company;
import models.project.Project;
import models.skill.Skill;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "developer")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "developer_id")
    private int developerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "city")
    private String city;

    @Column(name = "salary")
    private Integer salary;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Skill.class)
    @JoinTable(name = "developers_skills",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Project.class)
    @JoinTable(name = "developers_projects",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects = new HashSet<>();
}
