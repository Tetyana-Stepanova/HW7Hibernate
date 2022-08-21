package web.command.customer;

import models.customer.Customer;
import models.customer.CustomerService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import serviceprovider.ServiceProvider;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerGetByIdCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("customer", "");
        params.put("errorGetById", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setId") != null){
            CustomerService customerService = ServiceProvider.get(CustomerService.class);
            String id = req.getParameter("setId");
            Customer customer = null;
            String error = "";

            try {
                customer = customerService.getById(Integer.parseInt(id));
            } catch (Exception e) {
                error = e.getMessage();
            }

            params.replace("customer", customer == null ? "" : customer);
            params.replace("errorGetById", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("customer-getById", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
