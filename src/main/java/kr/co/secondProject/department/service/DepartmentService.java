package kr.co.secondProject.department.service;

import java.util.List;

import kr.co.secondProject.department.dto.ReqDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResDepartmentListDTO;

public interface DepartmentService {
	
	public List<ResDepartmentListDTO> departmentList();
	
	public List<ResDepartmentListDTO> departmentSerch(String keyword);
	
	public void departmentUpdate(Long id, ReqDepartmentUpdateDTO request);
	
	public void departmentDelete(Long id);

}
