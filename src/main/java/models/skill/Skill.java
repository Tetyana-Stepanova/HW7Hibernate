package models.skill;

import jakarta.persistence.*;
import lombok.Data;
import models.developer.Developer;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int skillId;

    @Column(name = "dev_language")
    private String devLanguage;

    @Column(name = "skill_level")
    private String skillLevel;

    @ManyToMany(targetEntity = Developer.class, mappedBy = "skills")
    private static Set<Developer> developers = new HashSet<>();
}
