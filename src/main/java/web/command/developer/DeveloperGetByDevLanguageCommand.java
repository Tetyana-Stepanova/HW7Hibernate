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

public class DeveloperGetByDevLanguageCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();

    static {
        params.put("developers", "");
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setDevLanguage") != null) {
            DeveloperService developerService = ServiceProvider.get(DeveloperService.class);
            String setDevLanguage = req.getParameter("setDevLanguage");
            List<Developer> developers = developerService.getByDevLanguage(setDevLanguage);
            params.replace("developers", developers == null ? "" : developers);
        }
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("developer-getByDevLanguage", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
