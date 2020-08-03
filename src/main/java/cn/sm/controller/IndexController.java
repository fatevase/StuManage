package cn.sm.controller;

import cn.sm.entity.ConfigKeyValue;
import cn.sm.entity.RespCode;
import cn.sm.util.util;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class IndexController {

    @GetMapping({"/","/stumanage","/index"})
    public String CheckIndexWeb( HttpServletRequest request) {
        if(util.CheckSignState(request)) {
            return "index";
        }else {
            return "login";
        }
    }
    @GetMapping("/login")
    public String LoginWeb(){
        return "login";
    }

    @GetMapping("/nav")
    public String NavWeb(){
        return "nav";
    }

    @GetMapping("/chooseManage")
    public String ChooseManage(){
        return "chooseManage";
    }

    @GetMapping("/courseManage")
    public String CourseManage(){
        return "courseManage";
    }
    @GetMapping("/courseMsg")
    public String CourseMsg(){
        return "courseMsg";
    }
    @GetMapping("/studentMsg")
    public String StudentMsg(){
        return "studentMsg";
    }
    @GetMapping("/teacherMsg")
    public String TeacherMsg(){
        return "teacherMsg";
    }
    @GetMapping("/userManage")
    public String UserManage(){
        return "userManage";
    }
    @GetMapping("/userManage_student")
    public String UserManageStudent(){
        return "userManage_student";
    }
    @GetMapping("/userManage_teacher")
    public String UserManageTeacher(){
        return "userManage_teacher";
    }
    @GetMapping("/personSet")
    public String PersonSet(){
        return "personSet";
    }

    @RequestMapping("/getnavbygroups")
    @ResponseBody
    public List<Map<String, String> > GetNavByGroups(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map<String, String> > result =  new ArrayList();
        Map<String, String> nav = new HashMap<>();
        nav.put("url","index");
        HttpSession session =  request.getSession();
        String groups = "";
        try{
            groups = session.getAttribute("groups").toString();
        }catch(Exception e){
            System.out.println(e);
        }

        switch(groups){
            case "admin":result = ConfigKeyValue.ADMIN_NAV;break;
            case "student":result = ConfigKeyValue.STUDENT_NAV;break;
            case "teacher":result = ConfigKeyValue.TEACHER_NAV;break;
            default: result.add(nav);break;
        }
        return result;
    }

    @RequestMapping("/getuloginname")
    @ResponseBody
    public RespCode GetLoginName(HttpServletRequest request){
        HttpSession session =  request.getSession();
        RespCode result = new RespCode();
        if(util.CheckSignState(request)) {
            result.setCode(1);
            result.setMsg(session.getAttribute("username").toString());
        }else{
            result.setCode(-1);
            result.setMsg("未登录账号");
        }

        return result;
    }

    @RequestMapping("/signout")
    @ResponseBody
    public RespCode SignOut(HttpServletRequest request){
        HttpSession session =  request.getSession();
        RespCode result = new RespCode();
        try {
            session.setAttribute("username", null);
            session.setAttribute("uid", null);
            session.setAttribute("groups", null);
            result.setCode(1);
            result.setMsg("注销成功");
        }catch(Exception e){
            result.setCode(-1);
            result.setMsg("注销失败");
        }
        return result;
    }

}
