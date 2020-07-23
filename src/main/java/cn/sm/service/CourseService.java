package cn.sm.service;


import cn.sm.mapper.CoursesMapper;
import cn.sm.mapper.UsersMapper;
import cn.sm.pojo.Courses;
import cn.sm.pojo.Users;
import cn.sm.pojo.ViewBean;
import cn.sm.util.timeSwitch;
import cn.sm.util.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {
    @Autowired
    CoursesMapper coursesMapper;
    @Autowired
    ChooseCourseService chooseCourseService;
    public List<Courses> GetAll() {
        return coursesMapper.GetAll();
    }

    public List<Courses> GetCourse(int id){
        List<Courses> courses = coursesMapper.GetCourseByID(id);
        return courses;
    }

    public int DeleteCourse(int id){
        Courses course = new Courses();
        course.setCoid(id);
        int result = 0;
        try{
            result = coursesMapper.DeleteCourse(course);
        }catch (Exception e){
            result = -1;
        }
        return result;
    }

    public int UpdateCourse(Map<String,Object> datamap){
        int result = 0;
        Courses course = new Courses();
        if(datamap.containsKey("coursecredit")) course.setCoursecredit(datamap.get("coursecredit").toString());
        if(datamap.containsKey("coursename")) course.setCoursename(datamap.get("coursename").toString());
        if(datamap.containsKey("teachername")) course.setTeachername(datamap.get("teachername").toString());
        if(datamap.containsKey("teacherid")) course.setTeacherid(Integer.parseInt(datamap.get("teacherid").toString()));
        course.setCoid(Integer.parseInt(datamap.get("coid").toString()));
        try{
            result = coursesMapper.UpdateCourse(course);
        }catch (Exception e){
            result = -1;
        }

        return result;
    }
    public int InsertCourse(Map<String,Object> datamap){
        int result = 0;
        Courses course = new Courses();
        course.setCoursecredit(datamap.get("coursecredit").toString());
        course.setCoursename(datamap.get("coursename").toString());
        course.setTeachername(datamap.get("teachername").toString());
        course.setTeacherid(Integer.parseInt(datamap.get("teacherid").toString()));

        try{
            result = coursesMapper.InsertCourse(course);
        }catch (Exception e){
            result = -1;
        }
        return result;
    }
    public List<Courses> FindCourse(Map<String,Object> datamap){
        List<Courses> result = null;
        Courses course = new Courses();
        String select_arg = "";
        if(datamap.containsKey("select_arg")) select_arg = datamap.get("select_arg").toString();
        switch(select_arg){
            case "coursecredit": course.setCoursecredit(datamap.get("arg_value").toString());break;
            case "coursename": course.setCoursename(datamap.get("arg_value").toString());break;
            case "teachername":  course.setTeachername(datamap.get("arg_value").toString());break;
            case "teacherid":  course.setTeacherid(Integer.parseInt(datamap.get("arg_value").toString()));break;
            default: course.setCoid(Integer.parseInt(datamap.get("arg_value").toString()));break;
        }

        result = coursesMapper.GetCourseByArg(course);
        return result;
    }

    public List<Map<String, Object> > GetTeacherCourse(HttpServletRequest request) {
        List<Map<String, Object> > result = new ArrayList<>();
        if(!util.CheckSignState(request)){
            return null;
        }
        //获取课程选择信息
        List<Courses> all_result = null;
        Courses course = new Courses();

        //获取所有教师教授的课程
        try{
            int get_uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            course.setTeacherid(get_uid);
            all_result = coursesMapper.GetCourseByArg(course);
        }catch(Exception e){
            System.out.println(e);
            return null;
        }

        //通过教授的课程的coid查找到对应授课时间
        Map<String,Object> map_data = null;
        Map<String,Object> find_data = null;

        for(int i = 0; i < all_result.size();i++){
            map_data = new HashMap<>();
            //通过chooseservice 进行查询对应的值
            find_data = new HashMap<>();
            find_data.put("select_arg","coid");
            find_data.put("arg_value",all_result.get(i).getCoid());
            System.out.println(all_result.get(i).getCoid());

            //通过chooseservice进行查询
            List<ViewBean> choose_data = chooseCourseService.FindChoose(find_data);

            if(choose_data.size()>0){
                map_data.put("starttime",choose_data.get(0).getStarttime());
                map_data.put("endtime",choose_data.get(0).getEndtime());
            }

            map_data.put("coursename",all_result.get(i).getCoursename());
            map_data.put("coursecredit",all_result.get(i).getCoursecredit());
            map_data.put("coid",all_result.get(i).getCoid());
            map_data.put("teacherid",all_result.get(i).getTeacherid());
            map_data.put("status",all_result.get(i).getTeacherid()>0?true:false);
            result.add(map_data);
        }

        return  result;
    }

    //教师选课更新数据库
    public int TeacherSelectCourse(Map<String,Object> datamap, HttpServletRequest request) {
        int result = 0;
        Map<String, Object> find_data = new HashMap<>();
        if (util.CheckSignState(request)) {
            HttpSession session =  request.getSession();
            // 更新课程表中的教师id
            int uid = Integer.parseInt(session.getAttribute("uid").toString());
            String username = session.getAttribute("username").toString();
            datamap.put("teacherid",uid);
            datamap.put("teachername",username);
            result = UpdateCourse(datamap);
            //更新课程数据
            if(result > 0){
                //更新选课表中所有选择该课的选课时间
                //通过chooseservice进行查询
                //查询到选课表中有人选择该课程 则同步课程
                //如果没人选课 则自己主动往选课表中插入该数据
                find_data.put("select_arg","coid");find_data.put("arg_value",datamap.get("coid"));
                List<ViewBean> get_choose =  chooseCourseService.FindChoose(find_data);
                if(get_choose.size()>0){
                    result = chooseCourseService.UpdateChoose(datamap);
                }else{
                    datamap.put("uid", uid);
                    result = chooseCourseService.InsertChoose(datamap);
                }
            }
        }
        return  result;
    }
}
