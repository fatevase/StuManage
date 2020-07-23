package cn.sm.mapper;

import cn.sm.pojo.Users;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository // bean 需要在application中设置扫描地址
// @mapper 不需要设置扫描地址
public interface UsersMapper {
	List<Users> GetAll();
	List<Users> CheckUserLogin(Users users);
	List<Users> GetUserByID(int id);
	int DeleteUser(Users users);
	int UpdateUser(Users users);
	int InsertUserAccount(Users users);
	List<Users> GetUserByArg(Users users);
}

