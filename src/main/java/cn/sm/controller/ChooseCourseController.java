package cn.sm.controller;

import cn.sm.entity.RespCode;
import cn.sm.pojo.ChooseCourse;
import cn.sm.pojo.Courses;
import cn.sm.pojo.Users;
import cn.sm.pojo.ViewBean;
import cn.sm.service.ChooseCourseService;
import cn.sm.service.CourseService;
import cn.sm.util.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/choosecourse")
public class ChooseCourseController {
	@Autowired
	private ChooseCourseService chooseCourseService;
	
	@RequestMapping("/getall")
	public List<ViewBean> findAll(){
		List<ViewBean> lc =  chooseCourseService.GetAll();
		return lc;
	}

	@RequestMapping("/getchoose")
	@ResponseBody
	public List<ViewBean> GetChoose(@RequestBody Map<String,Object> params, HttpServletRequest request){
		if(params.size()<1) {
			return null;
		}
		int id = Integer.parseInt(String.valueOf(params.get("ccid")));
		List<ViewBean> lc =  chooseCourseService.GetChoose(id);
		return lc;
	}
	@RequestMapping("/deletechoose")
	@ResponseBody
	public RespCode DeleteChoose(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}
		int id = Integer.parseInt(params.get("ccid").toString());

		int result = chooseCourseService.DeleteChoose(id);
		RespCode respCode = new RespCode();
		switch (result){
			case 0: {respCode.setCode(0);respCode.setMsg("未找到该选课");} break;
			case -1: {respCode.setCode(-1);respCode.setMsg("删除失败");} break;
			case 1: {respCode.setCode(1);respCode.setMsg("删除成功");} break;
			default:break;
		}
		return respCode;
	}
	@RequestMapping("/insertchoose")
	@ResponseBody
	public RespCode InsertChoose(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}
		int result = chooseCourseService.InsertChoose(params);
		RespCode respCode = new RespCode();
		switch (result){
			case 0: {respCode.setCode(0);respCode.setMsg("插入失败-未插入元素");} break;
			case -1: {respCode.setCode(-1);respCode.setMsg("插入失败-数据库问题");} break;
			case 1: {respCode.setCode(1);respCode.setMsg("插入成功");} break;
			default:break;
		}
		return respCode;
	}
	@RequestMapping("/updatechoose")
	@ResponseBody
	public RespCode UpdateChoose(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}

		int result = chooseCourseService.UpdateChoose(params);
		RespCode respCode = new RespCode();
		switch (result){
			case 0: {respCode.setCode(0);respCode.setMsg("更新失败-未找到元素");} break;
			case -1: {respCode.setCode(-1);respCode.setMsg("更新失败-数据库问题");} break;
			case 1: {respCode.setCode(1);respCode.setMsg("更新成功");} break;
			default:break;
		}
		return respCode;
	}
	@RequestMapping("/findchoose")
	@ResponseBody
	public List<ViewBean> FindChoose(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}
		List<ViewBean> result = chooseCourseService.FindChoose(params);

		return result;
	}

	@RequestMapping("/getusercourse")
	@ResponseBody
	public RespCode GetUserCourse(HttpServletRequest request) {
		RespCode respCode = new RespCode();
		List<Map<String,Object> > get_result = chooseCourseService.GetUserSelectedCourse(request);
		if(get_result==null){
			respCode.setCode(-1);respCode.setMsg("数据库操作有误");
		}else if(get_result.size()<1){
			respCode.setCode(0);respCode.setMsg("获取用户数据失败");
		}else{
			respCode.setCode(1);respCode.setMsg("获取用户数据成功");
			respCode.setData(get_result);
		}
		return respCode;
	}


	@RequestMapping("/selectedcourse")
	@ResponseBody
	public RespCode SelectedCourse(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		RespCode respCode = new RespCode();
		int get_result = chooseCourseService.SelectCourse(params,request);
		if(get_result<0){
			respCode.setCode(get_result);respCode.setMsg("数据库操作有误");
		}else if(get_result==0){
			respCode.setCode(0);respCode.setMsg("获取选课数据失败");
		}else{
			respCode.setCode(1);respCode.setMsg("获取选课数据成功");
		}
		return respCode;
	}

}
