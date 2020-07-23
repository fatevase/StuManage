package cn.sm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.sm.entity.RespCode;
import cn.sm.pojo.Users;
import cn.sm.util.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import cn.sm.service.UsersService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UsersService usersService;
	
	@RequestMapping("/getall")
	public List<Users> findAll(){

		List<Users> lc =  usersService.GetAll();
		return lc;
	}


	//@ResponseBody //用于传递json到前端
	//RequestBody 用于接收前端json数据
	@RequestMapping("/checkuserlogin")
	@ResponseBody
	public RespCode CheckUserLogin(@RequestBody Map<String,Object> params, HttpServletRequest request){
		int result = usersService.CheckUserLogin(params,request);
			RespCode respCode = new RespCode();
			switch (result){
				case 0: {respCode.setCode(0);respCode.setMsg("登录数据不全");} break;
				case -1: {respCode.setCode(-1);respCode.setMsg("登录失败");} break;
				case 1: {respCode.setCode(1);respCode.setMsg("登录成功");} break;
				default:break;
			}
		return respCode;
	}

	@RequestMapping("/getuser")
	@ResponseBody
	public List<Users> GetUser(@RequestBody Map<String,Object> params, HttpServletRequest request){
		if(params.size()<1) {
			return null;
		}
		int id = Integer.parseInt(String.valueOf(params.get("uid")));
		List<Users> lc =  usersService.GetUser(id);
		return lc;
	}
	@RequestMapping("/deleteuser")
	@ResponseBody
	public RespCode DeleteUser(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}
		int id = Integer.parseInt(params.get("uid").toString());

		int result = usersService.DeleteUser(id);
		RespCode respCode = new RespCode();
		switch (result){
			case 0: {respCode.setCode(0);respCode.setMsg("未找到该用户");} break;
			case -1: {respCode.setCode(-1);respCode.setMsg("删除失败");} break;
			case 1: {respCode.setCode(1);respCode.setMsg("删除成功");} break;
			default:break;
		}
		return respCode;
	}
	@RequestMapping("/insertuser")
	@ResponseBody
	public RespCode InsertUser(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}
		int result = usersService.InsertUser(params);
		RespCode respCode = new RespCode();
		switch (result){
			case 0: {respCode.setCode(0);respCode.setMsg("插入失败");} break;
			case -1: {respCode.setCode(-1);respCode.setMsg("插入失败");} break;
			case -2: {respCode.setCode(-2);respCode.setMsg("唯一学号已存在");} break;
			case 1: {respCode.setCode(1);respCode.setMsg("插入成功");} break;
			default:break;
		}
		return respCode;
	}
	@RequestMapping("/updateuser")
	@ResponseBody
	public RespCode UpdateUser(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}

		int result = usersService.UpdateUser(params);
		RespCode respCode = new RespCode();
		switch (result){
			case 0: {respCode.setCode(0);respCode.setMsg("更新失败");} break;
			case -1: {respCode.setCode(-1);respCode.setMsg("更新失败");} break;
			case 1: {respCode.setCode(1);respCode.setMsg("更新成功");} break;
			default:break;
		}
		return respCode;
	}
	@RequestMapping("/finduser")
	@ResponseBody
	public List<Users> FindUser(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		if(params.size()<1) {
			return null;
		}
		List<Users> result = usersService.FindUsers(params);

		return result;
	}

	@RequestMapping("/getlogindata")
	@ResponseBody
	public RespCode GetLoginData(HttpServletRequest request){
		RespCode respCode = new RespCode();
		Map<String, Object> getResult = usersService.GetLoginData(request);
		if(getResult==null){
			respCode.setCode(-1);respCode.setMsg("数据库操作有误");
		}else if(!getResult.containsKey("uid")){
			respCode.setCode(0);respCode.setMsg("获取用户数据失败");
		}else{
			respCode.setCode(1);respCode.setMsg("获取用户数据成功");
			List<Map<String, Object> > list_data = new ArrayList<>();
			list_data.add(getResult);
			respCode.setData(list_data);
		}
		return respCode;
	}

	@RequestMapping("/updateselfaccount")
	@ResponseBody
	public RespCode UpdateSelfAccount(@RequestBody Map<String,Object> params, HttpServletRequest request){
		RespCode respCode = new RespCode();
		if(util.CheckSignState(request)){
			params.put("uid",Integer.parseInt(request.getSession().getAttribute("uid").toString()));
			int getResult = usersService.UpdateUser(params);
			if(getResult<0){
				respCode.setCode(-1);respCode.setMsg("数据库操作有误");
			}else if(getResult==0){
				respCode.setCode(0);respCode.setMsg("获取用户数据失败");
			}else{
				respCode.setCode(1);respCode.setMsg("获取用户数据成功");
			}
		}
		return respCode;
	}

	@RequestMapping("/updatepassword")
	@ResponseBody
	public RespCode UpdatePassword(@RequestBody Map<String,Object> params, HttpServletRequest request){
		RespCode respCode = new RespCode();
		if(util.CheckSignState(request)){
			int uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
			Users user = usersService.GetUser(uid).get(0);
			if(user.getPassword().equals(params.get("oldpassword").toString())){
				params.put("uid",uid);
				int getResult = usersService.UpdateUser(params);
				if(getResult<0){
					respCode.setCode(-1);respCode.setMsg("数据库操作有误");
				}else if(getResult==0){
					respCode.setCode(0);respCode.setMsg("更新密码失败");
				}else{
					respCode.setCode(1);respCode.setMsg("更新密码成功");
				}
			}else{
				respCode.setCode(-2);respCode.setMsg("输入旧密码不匹配");
			}
		}
		return respCode;
	}

	@RequestMapping("/userstatistics")
	@ResponseBody
	public RespCode UserStatistics(@RequestBody Map<String,Object> params, HttpServletRequest request){
		RespCode respCode = new RespCode();
		List<Map<String, Object> > result = new ArrayList<>();

		List<Users> list_users = usersService.GetAll();
		if(params.size()<1) {
			respCode.setCode(-1);respCode.setMsg("无效API");
		} else{
			result.add(usersService.UserStatistics(params));
			if(result.size()>0){
				respCode.setCode(1);
				respCode.setMsg("success");
				respCode.setData(result);
			}else{
				respCode.setCode(0);
				respCode.setMsg("fail");
			}
		}

		return respCode;
	}
}
