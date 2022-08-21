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
import java.util.List;
import java.util.Map;

public class GetAllCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("companies", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST")){
            CompanyService companyService = ServiceProvider.get(CompanyService.class);
            List<Company> companies = companyService.getAll();
            params.replace("companies", companies == null ? "" : companies);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("company-getAll", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
