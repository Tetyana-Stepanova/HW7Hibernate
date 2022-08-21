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
import java.util.List;
import java.util.Map;

public class SkillGetAllCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("skills", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST")){
            SkillService skillService = ServiceProvider.get(SkillService.class);
            List<Skill> skills = skillService.getAll();
            params.replace("skills", skills == null ? "" : skills);
        }
        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("skill-getAll", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
