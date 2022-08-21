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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectGetAllCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("projects", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST")){
            ProjectService projectService = ServiceProvider.get(ProjectService.class);
            List<Project> projects = projectService.getAll();
            params.replace("projects", projects == null ? "" : projects);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("project-getAll", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
