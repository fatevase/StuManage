package cn.sm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sm.entity.RespCode;
import cn.sm.pojo.Users;
import cn.sm.util.timeSwitch;
import cn.sm.util.util;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.sm.mapper.UsersMapper;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Service
public class UsersService {
	@Autowired
	UsersMapper usersMapper;
	
	public List<Users> GetAll() {
		return usersMapper.GetAll();
	}

	public int CheckUserLogin(Map<String,Object> params, HttpServletRequest request) {
		String username ="",password="";
		if(params.size()<2) {
			return 0;
		}
		username = params.get("username").toString();
		password = params.get("password").toString();
		if(util.IsStrEmpty(username) ||util.IsStrEmpty(password)) {
			return 0;
		}
		//对 html 标签进行转义，防止 XSS 攻击
		username = HtmlUtils.htmlEscape(username);
		password = HtmlUtils.htmlEscape(password);
		//
		Users user = new Users();
		if(util.isNumeric(username)){
			user.setSchoolid(Integer.parseInt(username));
		}else{
			user.setUsername(username);
		}
		user.setPassword(password);

		List<Users> lu = usersMapper.CheckUserLogin(user);
		if(lu.size() == 1) {
			HttpSession session =  request.getSession();
			session.setAttribute("username",lu.get(0).getUsername());
			session.setAttribute("uid",lu.get(0).getUid());
			session.setAttribute("groups",lu.get(0).getGroups());
			return 1;
		}
		return -1;

	}
	public List<Users> GetUser(int id){
		List<Users> users = usersMapper.GetUserByID(id);
		return users;
	}

	public int DeleteUser(int id){
		Users user = new Users();
		user.setUid(id);
		int result = 0;

		//try catch 捕获删除错误异常
		try{
			result = usersMapper.DeleteUser(user);
		}catch (Exception e){
			result = -1;
		}
		return result;
	}

	public int UpdateUser(Map<String,Object> datamap){
		int result = 0;
		Users user = new Users();
		if(datamap.containsKey("password")) user.setPassword(datamap.get("password").toString());
		if(datamap.containsKey("username"))user.setUsername(datamap.get("username").toString());
		if(datamap.containsKey("age"))user.setAge(Integer.parseInt(datamap.get("age").toString()));
		if(datamap.containsKey("hometown"))user.setHometown(datamap.get("hometown").toString());
		if(datamap.containsKey("department"))user.setDepartment(datamap.get("department").toString());
		if(datamap.containsKey("uclass"))user.setUclass(datamap.get("uclass").toString());
		if(datamap.containsKey("sex"))user.setSex(datamap.get("sex").toString());
		if(datamap.containsKey("schoolid"))user.setSchoolid(Integer.parseInt(datamap.get("schoolid").toString()));
		if(datamap.containsKey("groups"))user.setGroups(datamap.get("groups").toString());
		user.setUid(Integer.parseInt(datamap.get("uid").toString()));
		try{
			result = usersMapper.UpdateUser(user);
		}catch (Exception e){
			result = -1;
		}
		return result;
	}
	public int InsertUser(Map<String,Object> datamap){
		int result = 0;
		Users user = new Users();
		Users check_user = new Users();
		if(datamap.containsKey("password")) user.setPassword(datamap.get("password").toString());
		if(datamap.containsKey("username")) user.setUsername(datamap.get("username").toString());
		if(datamap.containsKey("age")) user.setAge(Integer.parseInt(datamap.get("age").toString()));
		if(datamap.containsKey("hometown")) user.setHometown(datamap.get("hometown").toString());
		if(datamap.containsKey("department")) user.setDepartment(datamap.get("department").toString());
		if(datamap.containsKey("uclass")) user.setUclass(datamap.get("uclass").toString());
		if(datamap.containsKey("sex")) user.setSex(datamap.get("sex").toString());
		if(datamap.containsKey("groups")) user.setGroups(datamap.get("groups").toString());
		if(datamap.containsKey("schoolid")) {
			user.setSchoolid(Integer.parseInt(datamap.get("schoolid").toString()));
			check_user.setSchoolid(Integer.parseInt(datamap.get("schoolid").toString()));
		}

		user.setValidatekey(0);
		user.setCreated(timeSwitch.getUnixTime());
		user.setActivated(timeSwitch.getUnixTime());



		List<Users> check_list = usersMapper.GetUserByArg(check_user);
		if(check_list.size()>0){
			return -2;
			// 该唯一学号已存在
		}
		try{
			result = usersMapper.InsertUserAccount(user);
		}catch (Exception e){
			result = -1;
		}
		return result;
	}

	public List<Users> FindUsers(Map<String,Object> datamap){
		List<Users> result = null;
		Users user = new Users();
		String select_arg = "";
		if(datamap.containsKey("select_arg")) select_arg = datamap.get("select_arg").toString();
		switch(select_arg){
			case "username": user.setUsername(datamap.get("arg_value").toString());break;
			case "age": user.setAge(Integer.parseInt(datamap.get("arg_value").toString()));break;
			case "hometown": user.setHometown(datamap.get("arg_value").toString());break;
			case "department": user.setDepartment(datamap.get("arg_value").toString());break;
			case "uclass": user.setUclass(datamap.get("arg_value").toString());break;
			case "schoolid": user.setSchoolid(Integer.parseInt(datamap.get("arg_value").toString()));break;
			case "groups": user.setGroups(datamap.get("arg_value").toString());break;
			default: user.setUid(Integer.parseInt(datamap.get("arg_value").toString()));break;
		}
		/**
		if(datamap.containsKey("id")) user.setSchoolid(Integer.parseInt(datamap.get("id").toString()));
		if(datamap.containsKey("username")) user.setUsername(datamap.get("username").toString());
		if(datamap.containsKey("age")) user.setAge(Integer.parseInt(datamap.get("age").toString()));
		if(datamap.containsKey("hometown")) user.setHometown(datamap.get("hometown").toString());
		if(datamap.containsKey("department")) user.setDepartment(datamap.get("department").toString());
		if(datamap.containsKey("uclass")) user.setUclass(datamap.get("uclass").toString());
		if(datamap.containsKey("schoolid")) user.setSchoolid(Integer.parseInt(datamap.get("schoolid").toString()));
		if(datamap.containsKey("groups")) user.setGroups(datamap.get("groups").toString());
		 **/
		result = usersMapper.GetUserByArg(user);
		return result;
	}

	public Map<String, Object> GetLoginData(HttpServletRequest request){
		Map<String, Object> result = new HashMap<>();
		if(!util.CheckSignState(request)){
			return null;
		}
		try{
			int get_uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
			List<Users> list = GetUser(get_uid);
			result.put("uid",get_uid);
			result.put("schoolid",list.get(0).getSchoolid());
			result.put("username",list.get(0).getUsername());
			result.put("department",list.get(0).getDepartment());
			result.put("uclass",list.get(0).getUclass());

			result.put("sex",list.get(0).getSex());
			result.put("age",list.get(0).getAge());
			result.put("hometown",list.get(0).getHometown());
		}catch(Exception e){
			System.out.println(e);
			return null;
		}

		return  result;
	}

	public Map<String, Object> UserStatistics(Map<String, Object> params){
		Map<String, Object> result = new HashMap<>();
		List<Users> list_users = usersMapper.GetAll();
		String sort_key = params.get("sort").toString();
		/*
		switch (params.get("sort").toString()){
			case "groups": result.put("admin",0);result.put("teacher",0);result.put("student",0); break;
			case "sex": result.put("male",0);result.put("female",0);break;
			case "department": result.put("sort","department");break;
			case "uclass": result.put("sort","uclass");break;
		}
		 */

		if("groups".equals(sort_key)){
			result.put("admin",0);result.put("teacher",0);result.put("student",0);
			for(int i = 0 ;i < list_users.size();i++){
				String key = list_users.get(i).getGroups().toString();
						result.put(key,Integer.parseInt(result.get(key).toString())+1);
			}
		}else if("sex".equals(sort_key)){
			result.put("male",0);result.put("female",0);
			for(int i = 0 ;i < list_users.size();i++){
				String key = "男".equals(list_users.get(i).getSex())?"male":"female";
				result.put(key,Integer.parseInt(result.get(key).toString())+1);
			}
		}else if("department".equals(sort_key)){
			for(int i = 0 ;i < list_users.size();i++){
				String key = list_users.get(i).getDepartment();
				if(result.containsKey(key)){
					result.put(key,Integer.parseInt(result.get(key).toString())+1);
				}else{
					result.put(key,1);
				}
			}
		}else if("uclass".equals(sort_key)){
			for(int i = 0 ;i < list_users.size();i++){
				String key = list_users.get(i).getUclass();
				if(result.containsKey(key)){
					result.put(key,Integer.parseInt(result.get(key).toString())+1);
				}else{
					result.put(key,1);
				}
			}
		}
		return  result;
	}

}
