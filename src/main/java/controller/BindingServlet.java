package controller;

import com.alibaba.fastjson.JSON;
import model.Result;
import service.ProjectService;

import javax.naming.Binding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "binding",urlPatterns = "/api/v1/project/binding")
public class BindingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldKey  = req.getParameter("oldID");
        String newIDStr = req.getParameter("newID");
        int newID = -1;
        if(newIDStr!=null) newID=Integer.parseInt(newIDStr);
        String toolName = req.getParameter("toolName");
        if (toolName == null || toolName.length() == 0) {
            String[] strings = req.getHeader("referer").split("/");
            toolName = strings[3];
        }
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json,charset=UTF-8");

        String username = ((Map<String,String>) req.getSession().getAttribute("userInfo")).get("username");
        String resultKey = req.getParameter("resultKey");
        ProjectService projectService = new ProjectService(toolName);
        Result result = projectService.updateProjectRecord(oldKey,newID,resultKey,username);
        resp.getWriter().println(JSON.toJSONString(result));
    }
}
