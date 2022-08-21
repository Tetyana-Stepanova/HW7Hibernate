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

public class CustomerCreateCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("customerId", "");
        params.put("errorCreate", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setName") != null){
            CustomerService customerService = ServiceProvider.get(CustomerService.class);
            String setName = req.getParameter("setName");
            String setDescription = req.getParameter("setDescription");

            String id = "";
            String error = "";
            try {
                Customer customer = new Customer();
                customer.setCustomerName(setName);
                customer.setCustomerDescriptions(setDescription);
                customerService.create(customer);
                id = String.valueOf(customer.getCustomerId());
            }catch (Exception e) {
                error = e.getMessage();
            }

            params.replace("customerId", id);
            params.replace("errorCreate", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("customer-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
        }
}
