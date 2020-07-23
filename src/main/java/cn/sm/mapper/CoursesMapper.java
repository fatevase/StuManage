package cn.sm.mapper;

import cn.sm.pojo.Courses;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository // bean 需要在application中设置扫描地址
// @mapper 不需要设置扫描地址
public interface CoursesMapper {
	List<Courses> GetAll();
	List<Courses> GetCourseByID(int id);
	int DeleteCourse(Courses course);
	int UpdateCourse(Courses course);
	int InsertCourse(Courses course);
	List<Courses> GetCourseByArg(Courses course);
}

