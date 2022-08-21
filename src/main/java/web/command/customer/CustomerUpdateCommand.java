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

public class CustomerUpdateCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("errorUpdate", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("updateId") != null){
            CustomerService customerService = ServiceProvider.get(CustomerService.class);
            String setId = req.getParameter("updateId");
            String setName = req.getParameter("updateName");
            String setDescription = req.getParameter("updateDescription");

            String error = "";

            try {
                Customer customer = new Customer();
                customer.setCustomerId(Integer.parseInt(setId));
                customer.setCustomerName(setName);
                customer.setCustomerDescriptions(setDescription);
                error = customerService.update(customer);
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

        engine.process("customer-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
