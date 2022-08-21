package web.command.developer;

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
import java.util.Map;

public class DeveloperUpdateCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("errorUpdate", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("updateId") != null){
            DeveloperService developerService = ServiceProvider.get(DeveloperService.class);
            String setId = req.getParameter("updateId");
            String setFirstName = req.getParameter("updateFirstName");
            String setLastName = req.getParameter("updateLastName");
            String setAge = req.getParameter("updateAge");
            String setGender = req.getParameter("updateGender");
            String setCity = req.getParameter("updateCity");
            String setSalary = req.getParameter("updateSalary");
            String setCompanyId = req.getParameter("updateCompanyId");
            String setSkills = req.getParameter("updateSkills");
            String setProjects = req.getParameter("updateProjects");

            String error = "";

            try {
                Developer developer = new Developer();
                developer.setDeveloperId(Integer.parseInt(setId));
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

                error = developerService.update(developer, skills, companyId, projects);
            }catch (Exception e) {
                error = e.getMessage();
            }

            params.replace("errorUpdate", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("developer-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
        }
}
