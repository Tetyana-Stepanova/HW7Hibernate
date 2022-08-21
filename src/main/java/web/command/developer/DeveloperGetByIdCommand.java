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
import java.util.Map;

public class DeveloperGetByIdCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("developer", "");
        params.put("errorGetById", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setId") != null){
            DeveloperService developerService = ServiceProvider.get(DeveloperService.class);
            String id = req.getParameter("setId");
            Developer developer = null;
            String error = "";

            try {
                developer = developerService.getById(Integer.parseInt(id));
            } catch (Exception e) {
                error = e.getMessage();
            }

            params.replace("developer", developer == null ? "" : developer);
            params.replace("errorGetById", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("developer-getById", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
