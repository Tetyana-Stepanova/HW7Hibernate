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
import java.util.List;
import java.util.Map;

public class CustomerGetAllCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("customers", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST")){
            CustomerService customerService = ServiceProvider.get(CustomerService.class);
            List<Customer> customers = customerService.getAll();
            params.replace("customers", customers == null ? "" : customers);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("customer-getAll", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
