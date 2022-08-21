package web.command.project;

import models.company.Company;
import models.company.CompanyService;
import models.project.Project;
import models.project.ProjectService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import serviceprovider.ServiceProvider;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProjectGetByIdCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("project", "");
        params.put("errorGetById", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setId") != null){
            ProjectService projectService = ServiceProvider.get(ProjectService.class);
            String id = req.getParameter("setId");
            Project project = null;
            String error = "";

            try {
                project = projectService.getById(Integer.parseInt(id));
            } catch (Exception e) {
                error = e.getMessage();
            }

            params.replace("project", project == null ? "" : project);
            params.replace("errorGetById", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("project-getById", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
