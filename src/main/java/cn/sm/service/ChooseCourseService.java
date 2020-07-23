package cn.sm.service;


import cn.sm.mapper.ChooseCourseMapper;
import cn.sm.mapper.CoursesMapper;
import cn.sm.pojo.ChooseCourse;
import cn.sm.pojo.Courses;
import cn.sm.pojo.ViewBean;
import cn.sm.util.timeSwitch;
import cn.sm.util.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChooseCourseService {
    @Autowired
    ChooseCourseMapper chooseCourseMapper;
    public List<ViewBean> GetAll() {
        return chooseCourseMapper.GetAll();
    }

    public List<ViewBean> GetChoose(int id){
        List<ViewBean> result = chooseCourseMapper.GetChooseByID(id);
        return result;
    }

    public int DeleteChoose(int id){
        ChooseCourse choose = new ChooseCourse();
        choose.setCcid(id);
        int result = 0;
        try{
            result = chooseCourseMapper.DeleteChoose(choose);
        }catch (Exception e){
            result = -1;
        }
        return result;
    }

    public int UpdateChoose(Map<String,Object> datamap){
        int result = 0;
        ChooseCourse choose = new ChooseCourse();
        if(datamap.containsKey("coid")) choose.setCoid(Integer.parseInt(datamap.get("coid").toString()));
        if(datamap.containsKey("uid")) choose.setUid(Integer.parseInt(datamap.get("uid").toString()));
        if(datamap.containsKey("starttime")) choose.setStarttime(Integer.parseInt(datamap.get("starttime").toString()));
        if(datamap.containsKey("endtime")) choose.setEndtime(Integer.parseInt(datamap.get("endtime").toString()));
        if(datamap.containsKey("choosetime")) choose.setChoosetime(Integer.parseInt(datamap.get("choosetime").toString()));
        if(datamap.containsKey("ccid")) choose.setCcid(Integer.parseInt(datamap.get("ccid").toString()));

        System.out.println(choose.getCcid()+"---------"+choose.getCoid());
        try{
            result = chooseCourseMapper.UpdateChoose(choose);
        }catch (Exception e){
            System.out.println(e);
            result = -1;
        }
        return result;
    }
    public int InsertChoose(Map<String,Object> datamap){
        int result = 0;
        ChooseCourse choose = new ChooseCourse();
        choose.setCoid(Integer.parseInt(datamap.get("coid").toString()));
        choose.setUid(Integer.parseInt(datamap.get("uid").toString()));
        if(datamap.containsKey("starttime")) choose.setStarttime(Integer.parseInt(datamap.get("starttime").toString()));
        if(datamap.containsKey("endtime")) choose.setEndtime(Integer.parseInt(datamap.get("endtime").toString()));
        choose.setChoosetime(timeSwitch.getUnixTime());

        try{
            result = chooseCourseMapper.InsertChoose(choose);
        }catch (Exception e){
            System.out.println(e);
            result = -1;
        }
        return result;
    }
    public List<ViewBean> FindChoose(Map<String,Object> datamap){
        List<ViewBean> result = null;
        ViewBean viewBean = new ViewBean();
        String select_arg = "";
        if(datamap.containsKey("select_arg") && datamap.containsKey("arg_value") ) select_arg = datamap.get("select_arg").toString();
        else return null;
        switch(select_arg){
            case "username": viewBean.setUsername(datamap.get("arg_value").toString());break;
            case "coursename": viewBean.setCoursename(datamap.get("arg_value").toString());break;
            case "uid": viewBean.setUid(Integer.parseInt(datamap.get("arg_value").toString()));break;
            case "coid": viewBean.setCoid(Integer.parseInt(datamap.get("arg_value").toString()));break;
            default: viewBean.setCcid(Integer.parseInt(datamap.get("arg_value").toString()));break;
        }

        System.out.println(datamap.size());
        result = chooseCourseMapper.GetChooseByArg(viewBean);
        return result;
    }

    public List<Map<String, Object> > GetUserSelectedCourse(HttpServletRequest request) {
        List<Map<String, Object> > result = new ArrayList<>();
        if(!util.CheckSignState(request)){
            return null;
        }
        List<ViewBean> all_result = null;
        ViewBean viewBean = new ViewBean();
        Map<String,Object> map_data = null;
        try{
            int get_uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            viewBean.setUid(get_uid);
            all_result = chooseCourseMapper.GetChooseByArg(viewBean);

        }catch(Exception e){
            System.out.println(e);
            return null;
        }

        for(int i = 0; i < all_result.size();i++){
            map_data = new HashMap<>();
            map_data.put("coursename",all_result.get(i).getCoursename());
            map_data.put("teachername",all_result.get(i).getTeachername());
            map_data.put("coursecredit",all_result.get(i).getCoursecredit());
            map_data.put("starttime",all_result.get(i).getStarttime());
            map_data.put("endtime",all_result.get(i).getEndtime());
            map_data.put("chosetime",all_result.get(i).getChoosetime());
            map_data.put("ccid",all_result.get(i).getCcid());
            map_data.put("coid",all_result.get(i).getCoid());
            result.add(map_data);
        }

        return  result;
    }

    public int SelectCourse(Map<String,Object> datamap, HttpServletRequest request) {
        int result = 0;
        Map<String, Object> find_data = new HashMap<>();
        if (util.CheckSignState(request)) {
            HttpSession session =  request.getSession();
            int uid = Integer.parseInt(session.getAttribute("uid").toString());
            find_data.put("select_arg","coid");find_data.put("arg_value",datamap.get("coid"));
            List<ViewBean> get_choose =  FindChoose(find_data);
            if(get_choose.size()>0){
                datamap.put("starttime",get_choose.get(0).getStarttime());
                datamap.put("endtime",get_choose.get(0).getEndtime());
            }
            datamap.put("uid",uid);
            result = InsertChoose(datamap);
        }
        return  result;
    }
}
