package kr.co.secondProject.department.service;

import java.util.List;

import kr.co.secondProject.department.dto.ReqDepartmentCreateDTO;
import kr.co.secondProject.department.dto.ReqDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResCurrentDpListDTO;
import kr.co.secondProject.department.dto.ResDepartmentCreateDTO;
import kr.co.secondProject.department.dto.ResDepartmentListDTO;
import kr.co.secondProject.department.dto.ResDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResUpdateMemberListDTO;

public interface DepartmentService {
	
	public List<ResDepartmentListDTO> departmentList(String keyword);
	
	public ResDepartmentUpdateDTO departmentUpdate(Long id, ReqDepartmentUpdateDTO request);
	
	public void departmentDelete(Long id);
	
	public ResDepartmentCreateDTO departmentCreate(ReqDepartmentCreateDTO request);

	public List<ResCurrentDpListDTO> currentDpList();

	public List<ResUpdateMemberListDTO> updateList(Long id);
}
