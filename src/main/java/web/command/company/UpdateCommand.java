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

public class UpdateCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("errorUpdate", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("updateId") != null){
            CompanyService companyService = ServiceProvider.get(CompanyService.class);
            String setId = req.getParameter("updateId");
            String setName = req.getParameter("updateName");
            String setDescription = req.getParameter("updateDescription");

            String error = "";

            try {
                Company company = new Company();
                company.setCompanyId(Integer.parseInt(setId));
                company.setCompanyName(setName);
                company.setCompanyDescription(setDescription);
                error = companyService.update(company);
            } catch (Exception e) {
                error = e.getMessage();
            }

            params.replace("errorUpdate", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("company-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
