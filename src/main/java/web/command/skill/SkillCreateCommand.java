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

public class SkillCreateCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("skillId", "");
        params.put("errorCreate", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("setDevLanguage") != null){
            SkillService skillService = ServiceProvider.get(SkillService.class);

            String setDevLanguage = req.getParameter("setDevLanguage");
            String setSkillLevel = req.getParameter("setSkillLevel");
            String id = "";
            String error = "";
            try {
                Skill skill = new Skill();
                skill.setDevLanguage(setDevLanguage);
                skill.setSkillLevel(setSkillLevel);
                skillService.create(skill);
                id = String.valueOf(skill.getSkillId());
            }catch (Exception e) {
                error = e.getMessage();
            }
            params.replace("skillId", id);
            params.replace("errorCreate", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("skill-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
