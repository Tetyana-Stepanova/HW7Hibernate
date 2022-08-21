package web.command.developer;

import models.company.CompanyService;
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

public class DeveloperDeleteCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("errorDeleteById", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("deleteId") != null){
            DeveloperService developerService = ServiceProvider.get(DeveloperService.class);
            String id = req.getParameter("deleteId");

            String error = "";

            try {
                error = developerService.deleteById(Integer.parseInt(id));
            } catch (Exception e) {
                error = e.getMessage();
            }
            params.replace("errorDeleteById", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("developer-delete", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
