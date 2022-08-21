package serviceprovider;

import models.company.CompanyService;
import models.company.HibernateCompanyService;
import models.customer.CustomerService;
import models.customer.HibernateCustomerService;
import models.developer.DeveloperService;
import models.developer.HibernateDeveloperService;
import models.project.HibernateProjectService;
import models.project.ProjectService;
import models.skill.HibernateSkillService;
import models.skill.SkillService;

import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
    private static Map<Class<? extends Object>, Object> SERVICES = new HashMap<>();

    static {
        SERVICES.put(CompanyService.class, new HibernateCompanyService());
        SERVICES.put(CustomerService.class, new HibernateCustomerService());
        SERVICES.put(DeveloperService.class, new HibernateDeveloperService());
        SERVICES.put(SkillService.class, new HibernateSkillService());
        SERVICES.put(ProjectService.class, new HibernateProjectService());
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> tClass) {
        return (T) SERVICES.get(tClass);
    }
}
