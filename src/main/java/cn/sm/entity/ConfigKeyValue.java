package cn.sm.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigKeyValue {
    public void ConfigKeyValue(){}
    //学生权限导航内容
    public static final List<Map<String, String> > STUDENT_NAV;

    //教师权限导航内容
    public static final List<Map<String, String> > TEACHER_NAV;

    //管理员权限导航内容
    public static final List<Map<String, String> > ADMIN_NAV;
    static
    {
        STUDENT_NAV = new ArrayList<>();
        TEACHER_NAV = new ArrayList<>();
        ADMIN_NAV = new ArrayList<>();

        Map<String, String> nav = null;
        //map 每次需要new 因为list存入的map是地址
        nav = new HashMap<>();
        nav.put("url","index");nav.put("name","后台首页");STUDENT_NAV.add(nav);TEACHER_NAV.add(nav);ADMIN_NAV.add(nav);
        nav = new HashMap<>();
        nav.put("url","studentMsg");nav.put("name","用户信息");STUDENT_NAV.add(nav);
        nav = new HashMap<>();
        nav.put("url","teacherMsg");nav.put("name","教师信息");TEACHER_NAV.add(nav);
        nav = new HashMap<>();
        nav.put("url","courseMsg");nav.put("name","课程信息");STUDENT_NAV.add(nav);TEACHER_NAV.add(nav);
        nav = new HashMap<>();
        nav.put("url","chooseManage");nav.put("name","选课管理");ADMIN_NAV.add(nav);
        nav = new HashMap<>();
        nav.put("url","courseManage");nav.put("name","课程管理");ADMIN_NAV.add(nav);
        nav = new HashMap<>();
        nav.put("url","userManage");nav.put("name","用户管理");ADMIN_NAV.add(nav);
        nav = new HashMap<>();
        nav.put("url","personSet");nav.put("name","修改信息");
        STUDENT_NAV.add(nav);TEACHER_NAV.add(nav);ADMIN_NAV.add(nav);
    }
}
