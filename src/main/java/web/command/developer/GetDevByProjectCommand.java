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
import java.util.List;
import java.util.Map;

public class GetDevByProjectCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();

    static {
        params.put("developers", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setProjectId") != null) {
            DeveloperService developerService = ServiceProvider.get(DeveloperService.class);
            String setProjectId = req.getParameter("setProjectId");
            List<Developer> developers = developerService.getDevByProjectId(Integer.parseInt(setProjectId));
            params.replace("developers", developers == null ? "" : developers);
        }
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("developer-getDevByProject", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
