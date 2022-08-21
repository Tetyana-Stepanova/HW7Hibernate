package web.command.skill;

import models.skill.Skill;
import models.skill.SkillService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import serviceprovider.ServiceProvider;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SkillGetByIdCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("skill", "");
        params.put("errorGetById", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setId") != null){
            SkillService skillService = ServiceProvider.get(SkillService.class);
            String id = req.getParameter("setId");
            Skill skill = null;
            String error = "";

            try {
                skill = skillService.getById(Integer.parseInt(id));
            }catch (Exception e) {
                error = e.getMessage();
            }
            params.replace("skill", skill == null ? "" : skill);
            params.replace("errorGetById", error);
        }
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("skill-getById", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
