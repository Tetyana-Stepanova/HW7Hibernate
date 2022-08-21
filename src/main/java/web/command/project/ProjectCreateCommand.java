package web.command.project;

import models.project.Project;
import models.project.ProjectService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import serviceprovider.ServiceProvider;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ProjectCreateCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("projectId", "");
        params.put("errorCreate", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setName") != null){
            ProjectService projectService = ServiceProvider.get(ProjectService.class);
            String setName = req.getParameter("setName");
            String setDescription = req.getParameter("setDescription");
            String setReleaseDate = req.getParameter("setReleaseDate");
            String setCompanyId = req.getParameter("setCompanyId");
            String setCustomerId = req.getParameter("setCustomerId");

            String id = "";
            String error = "";
            try {
                Project project = new Project();
                project.setProjectName(setName);
                project.setProjectDescription(setDescription);
                project.setReleaseDate(LocalDate.parse(setReleaseDate));
                int companyId = Integer.parseInt(setCompanyId);
                int customerId = Integer.parseInt(setCustomerId);
                projectService.create(project, companyId, customerId);
                id = String.valueOf(project.getProjectId());
            }catch (Exception e) {
                error = e.getMessage();
            }

            params.replace("projectId", id);
            params.replace("errorCreate", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("project-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
        }
}
