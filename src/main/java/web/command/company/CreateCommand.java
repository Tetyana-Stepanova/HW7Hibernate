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

public class CreateCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("companyId", "");
        params.put("errorCreate", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setName") != null) {
            CompanyService companyService = ServiceProvider.get(CompanyService.class);
            String setName = req.getParameter("setName");
            String setDescription = req.getParameter("setDescription");

            String id = "";
            String error = "";
            try {
                Company company = new Company();
                company.setCompanyName(setName);
                company.setCompanyDescription(setDescription);
                companyService.create(company);
                id = String.valueOf(company.getCompanyId());
            } catch (Exception e) {
                error = e.getMessage();
            }

            params.replace("companyId", id);
            params.replace("errorCreate", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("company-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
