package web.command.company;

import models.company.Company;
import models.company.CompanyService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import serviceprovider.ServiceProvider;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetByIdCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("company", "");
        params.put("errorGetById", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setId") != null){
            CompanyService companyService = ServiceProvider.get(CompanyService.class);
            String id = req.getParameter("setId");
            Company company = null;
            String error = "";

            try {
                company = companyService.getById(Integer.parseInt(id));
            } catch (Exception e) {
                error = e.getMessage();
            }

            params.replace("company", company == null ? "" : company);
            params.replace("errorGetById", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("company-getById", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
