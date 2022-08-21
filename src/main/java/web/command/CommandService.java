package web.command;

import org.thymeleaf.TemplateEngine;
import storage.StorageConstants;
import web.command.company.*;
import web.command.customer.*;
import web.command.developer.*;
import web.command.project.*;
import web.command.skill.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class CommandService {
    private Connection connection;
    private Map<String, Command> commands;

    public CommandService() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(StorageConstants.CONNECTION_URL,
                    StorageConstants.USERNAME,
                    StorageConstants.PASSWORD);
        }catch (Exception e) {
            e.printStackTrace();
        }
        commands = new HashMap<>();

        commands.put("GET /", new MainCommand());

        commands.put("GET /company", new CompanyCommand());
        commands.put("POST /company/create", new CreateCommand());
        commands.put("GET /company-create", new CreateCommand());
        commands.put("POST /company/getById", new GetByIdCommand());
        commands.put("GET /company-getById", new GetByIdCommand());
        commands.put("GET /company-deleteById", new DeleteByIdCommand());
        commands.put("POST /company/deleteById", new DeleteByIdCommand());
        commands.put("GET /company-update", new UpdateCommand());
        commands.put("POST /company/update", new UpdateCommand());
        commands.put("GET /company-getAll", new GetAllCommand());
        commands.put("POST /company/getAll", new GetAllCommand());

        commands.put("GET /customer", new CustomerCommand());
        commands.put("GET /customer-create", new CustomerCreateCommand());
        commands.put("POST /customer/create", new CustomerCreateCommand());
        commands.put("GET /customer-delete", new CustomerDeleteCommand());
        commands.put("POST /customer/delete", new CustomerDeleteCommand());
        commands.put("GET /customer-getAll", new CustomerGetAllCommand());
        commands.put("POST /customer/getAll", new CustomerGetAllCommand());
        commands.put("GET /customer-getById", new CustomerGetByIdCommand());
        commands.put("POST /customer/getById", new CustomerGetByIdCommand());
        commands.put("GET /customer-update", new CustomerUpdateCommand());
        commands.put("POST /customer/update", new CustomerUpdateCommand());

        commands.put("GET /skill", new SkillCommand());
        commands.put("GET /skill-create", new SkillCreateCommand());
        commands.put("POST /skill/create", new SkillCreateCommand());
        commands.put("GET /skill-delete", new SkillDeleteCommand());
        commands.put("POST /skill/delete", new SkillDeleteCommand());
        commands.put("GET /skill-getAll", new SkillGetAllCommand());
        commands.put("POST /skill/getAll", new SkillGetAllCommand());
        commands.put("GET /skill-getById", new SkillGetByIdCommand());
        commands.put("POST /skill/getById", new SkillGetByIdCommand());
        commands.put("GET /skill-update", new SkillUpdateCommand());
        commands.put("POST /skill/update", new SkillUpdateCommand());

        commands.put("GET /developer", new DeveloperCommand());
        commands.put("GET /developer-create", new DeveloperCreateCommand());
        commands.put("POST /developer/create", new DeveloperCreateCommand());
        commands.put("GET /developer-getAll", new DeveloperGetAllCommand());
        commands.put("POST /developer/getAll", new DeveloperGetAllCommand());
        commands.put("GET /developer-delete", new DeveloperDeleteCommand());
        commands.put("POST /developer/delete", new DeveloperDeleteCommand());
        commands.put("GET /developer-getById", new DeveloperGetByIdCommand());
        commands.put("POST /developer/getById", new DeveloperGetByIdCommand());
        commands.put("GET /developer-update", new DeveloperUpdateCommand());
        commands.put("POST /developer/update", new DeveloperUpdateCommand());
        commands.put("GET /developer-getByDevLanguage", new DeveloperGetByDevLanguageCommand());
        commands.put("POST /developer/getByDevLanguage", new DeveloperGetByDevLanguageCommand());
        commands.put("GET /developer-getBySkillLevel", new GetBySkillLevelCommand());
        commands.put("POST /developer/getBySkillLevel", new GetBySkillLevelCommand());
        commands.put("GET /developer-getDevByProject", new GetDevByProjectCommand());
        commands.put("POST /developer/getDevByProject", new GetDevByProjectCommand());


        commands.put("GET /project", new ProjectCommand());
        commands.put("GET /project-create", new ProjectCreateCommand());
        commands.put("POST /project/create", new ProjectCreateCommand());
        commands.put("GET /project-delete", new ProjectDeleteCommand());
        commands.put("POST /project/delete", new ProjectDeleteCommand());
        commands.put("GET /project-getAll", new ProjectGetAllCommand());
        commands.put("POST /project/getAll", new ProjectGetAllCommand());
        commands.put("GET /project-getById", new ProjectGetByIdCommand());
        commands.put("POST /project/getById", new ProjectGetByIdCommand());
        commands.put("GET /project-update", new ProjectUpdateCommand());
        commands.put("POST /project/update", new ProjectUpdateCommand());
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String requestUri = req.getRequestURI();
        String commandKey = req.getMethod() + " " + requestUri;

        commands.get(commandKey).process(req, resp, engine);
    }
}
