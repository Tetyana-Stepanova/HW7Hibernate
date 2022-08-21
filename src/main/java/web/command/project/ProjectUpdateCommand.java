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

public class ProjectUpdateCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("errorUpdate", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("updateId") != null){
            ProjectService projectService = ServiceProvider.get(ProjectService.class);
            String updateId = req.getParameter("updateId");
            String updateName = req.getParameter("updateName");
            String updateDescription = req.getParameter("updateDescription");
            String updateReleaseDate = req.getParameter("updateReleaseDate");
            String updateCompanyId = req.getParameter("updateCompanyId");
            String updateCustomerId = req.getParameter("updateCustomerId");

            String error = "";
            try {
                Project project = new Project();
                project.setProjectId(Integer.parseInt(updateId));
                project.setProjectName(updateName);
                project.setProjectDescription(updateDescription);
                project.setReleaseDate(LocalDate.parse(updateReleaseDate));
                int companyId = Integer.parseInt(updateCompanyId);
                int customerId = Integer.parseInt(updateCustomerId);
                error = projectService.update(project, companyId, customerId);
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

        engine.process("project-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
        }
}
