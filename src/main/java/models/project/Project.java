package models.project;

import jakarta.persistence.*;
import lombok.Data;
import models.company.Company;
import models.customer.Customer;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "release_date", columnDefinition = "DATE")
    @ColumnDefault("")
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;
}
