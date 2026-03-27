package kr.co.secondProject.department.service;

import java.util.List;

import kr.co.secondProject.department.dto.ReqDepartmentListDTO;
import kr.co.secondProject.department.dto.ResDepartmentListDTO;

public interface DepartmentService {
	
	public List<ResDepartmentListDTO> departmentList();
	
	public List<ResDepartmentListDTO> departmentSerch(String keyword);
	
	public ResDepartmentListDTO departmentUpdate(Long id, ReqDepartmentListDTO request);
	
	public void departmentDelete(Long id);

}
