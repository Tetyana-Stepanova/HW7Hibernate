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

public class SkillUpdateCommand implements Command {
    private static Map<String, Object> params = new HashMap<>();
    static {
        params.put("errorUpdate", "");
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String method = req.getMethod();
        if (method.equals("POST") && req.getParameter("updateId") != null){
            SkillService skillService = ServiceProvider.get(SkillService.class);
            String setId = req.getParameter("updateId");
            String setDevLanguage = req.getParameter("updateDevLanguage");
            String setSkillLevel = req.getParameter("updateSkillLevel");

            String error = "";
            try{
                Skill skill = new Skill();
                skill.setSkillId(Integer.parseInt(setId));
                skill.setDevLanguage(setDevLanguage);
                skill.setSkillLevel(setSkillLevel);
                error = skillService.update(skill);
            }catch (Exception e) {
                error = e.getMessage();
            }

            params.replace("errorUpdate", error);
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("skill-update", simpleContext, resp.getWriter());
        resp.getWriter().close();

        }
}
