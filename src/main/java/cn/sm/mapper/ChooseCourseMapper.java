package cn.sm.mapper;

import cn.sm.pojo.ChooseCourse;
import cn.sm.pojo.Courses;
import cn.sm.pojo.ViewBean;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository // bean 需要在application中设置扫描地址
// @mapper 不需要设置扫描地址
public interface ChooseCourseMapper {
	List<ViewBean> GetAll();
	List<ViewBean> GetChooseByID(int id);
	int DeleteChoose(ChooseCourse choose);
	int UpdateChoose(ChooseCourse choose);
	int InsertChoose(ChooseCourse choose);
	List<ViewBean> GetChooseByArg(ViewBean viewBean);
}

