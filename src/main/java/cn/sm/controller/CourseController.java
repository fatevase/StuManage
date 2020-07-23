package cn.sm.controller;

import cn.sm.entity.RespCode;
import cn.sm.pojo.Courses;
import cn.sm.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired
	private CourseService courseService;
	
	@RequestMapping("/getall")
	public List<Courses> findAll(){
		List<Courses> lc =  courseService.GetAll();
		return lc;
	}

	@RequestMapping("/getcourse")
	@ResponseBody
	public List<Courses> GetCourse(@RequestBody Map<String,Object> params, HttpServletRequest request){
		if(params.size()<1) {
			return null;
		}
		int id = Integer.parseInt(String.valueOf(params.get("coid")));
		List<Courses> lc =  courseService.GetCourse(id);
		return lc;
	}
	@RequestMapping("/deletecourse")
	@ResponseBody
	public RespCode DeleteCourse(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}
		int id = Integer.parseInt(params.get("coid").toString());

		int result = courseService.DeleteCourse(id);
		RespCode respCode = new RespCode();
		switch (result){
			case 0: {respCode.setCode(0);respCode.setMsg("未找到该课程");} break;
			case -1: {respCode.setCode(-1);respCode.setMsg("删除失败");} break;
			case 1: {respCode.setCode(1);respCode.setMsg("删除成功");} break;
			default:break;
		}
		return respCode;
	}
	@RequestMapping("/insertcourse")
	@ResponseBody
	public RespCode InsertCourse(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}
		int result = courseService.InsertCourse(params);
		RespCode respCode = new RespCode();
		switch (result){
			case 0: {respCode.setCode(0);respCode.setMsg("插入失败");} break;
			case -1: {respCode.setCode(-1);respCode.setMsg("插入失败");} break;
			case 1: {respCode.setCode(1);respCode.setMsg("插入成功");} break;
			default:break;
		}
		return respCode;
	}
	@RequestMapping("/updatecourse")
	@ResponseBody
	public RespCode UpdateCourse(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}

		int result = courseService.UpdateCourse(params);
		RespCode respCode = new RespCode();
		switch (result){
			case 0: {respCode.setCode(0);respCode.setMsg("更新失败");} break;
			case -1: {respCode.setCode(-1);respCode.setMsg("更新失败");} break;
			case 1: {respCode.setCode(1);respCode.setMsg("更新成功");} break;
			default:break;
		}
		return respCode;
	}
	@RequestMapping("/findcourse")
	@ResponseBody
	public List<Courses> FindCourse(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}
		List<Courses> result = courseService.FindCourse(params);

		return result;
	}
	/*
	//获取用户所学课程
	@RequestMapping("/gettearchercourse")
	@ResponseBody
	public RespCode GetUserCourse(HttpServletRequest request) {
		RespCode respCode = new RespCode();
		List<Map<String,Object> > get_result = courseService.GetTeacherCourse(request);
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
	*/

	//获取教师选择过的课程
	@RequestMapping("/getselectedcourse")
	@ResponseBody
	public RespCode GetSelectedCourse(HttpServletRequest request) {
		RespCode respCode = new RespCode();
		List<Map<String,Object> > get_result = courseService.GetTeacherCourse(request);
		if(get_result==null){
			respCode.setCode(-1);respCode.setMsg("数据库操作有误");
		}else if(get_result.size()<1){
			respCode.setCode(0);respCode.setMsg("获取课程数据失败");
		}else{
			respCode.setCode(1);respCode.setMsg("获取课程数据成功");
			respCode.setData(get_result);
		}
		return respCode;
	}

	//教师进行选择课程
	@RequestMapping("/teachcourse")
	@ResponseBody
	//params ->coid ,starttime,endtime
	public RespCode TeachCourse(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		RespCode respCode = new RespCode();
		int get_result = courseService.TeacherSelectCourse(params, request);
		if(get_result<0){
			respCode.setCode(get_result);respCode.setMsg("数据库操作有误");
		}else if(get_result==0){
			respCode.setCode(0);respCode.setMsg("选课失败");
		}else{
			respCode.setCode(1);respCode.setMsg("选课成功");
		}
		return respCode;
	}
}
