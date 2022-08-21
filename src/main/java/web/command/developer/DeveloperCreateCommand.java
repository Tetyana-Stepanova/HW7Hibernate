package web.command.developer;

import models.company.Company;
import models.company.CompanyService;
import models.developer.Developer;
import models.developer.DeveloperService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import serviceprovider.ServiceProvider;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeveloperCreateCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("developerId", "");
        params.put("errorCreate", "");
        params.put("companies", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setFirstName") != null){
            DeveloperService developerService = ServiceProvider.get(DeveloperService.class);

            String setFirstName = req.getParameter("setFirstName");
            String setLastName = req.getParameter("setLastName");
            String setAge = req.getParameter("setAge");
            String setGender = req.getParameter("setGender");
            String setCity = req.getParameter("setCity");
            String setSalary = req.getParameter("setSalary");
            String setCompanyId = req.getParameter("setCompanyId");
            String setSkills = req.getParameter("setSkills");
            String setProjects = req.getParameter("setProjects");

            String id = "";
            String error = "";

            try {
                Developer developer = new Developer();
                developer.setFirstName(setFirstName);
                developer.setLastName(setLastName);
                developer.setAge(Integer.parseInt(setAge));
                developer.setGender(setGender);
                developer.setCity(setCity);
                developer.setSalary(Integer.parseInt(setSalary));

                int companyId = Integer.parseInt(setCompanyId);

                String[] values = setSkills.split(",");
                int[] skills = new int[values.length];
                for (int i = 0; i < skills.length; i++) {
                    skills[i] = Integer.parseInt(values[i].strip());
                }

                String[] pValues = setProjects.split(",");
                int[] projects = new int[pValues.length];
                for (int i = 0; i < projects.length; i++) {
                    projects[i] = Integer.parseInt(pValues[i].strip());
                }
                developerService.create(developer, skills, companyId, projects);
                id = String.valueOf(developer.getDeveloperId());
            }catch (Exception e) {
                error = e.getMessage();
            }
            params.replace("developerId", id);
            params.replace("errorCreate", error);
        }
        CompanyService companyService = ServiceProvider.get(CompanyService.class);
        List<Company> companies = companyService.getAll();
        params.replace("companies", companies == null ? "" : companies);

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("developer-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
