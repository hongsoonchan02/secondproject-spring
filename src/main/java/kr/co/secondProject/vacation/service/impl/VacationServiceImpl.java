package kr.co.secondProject.vacation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.co.secondProject.attendance.dto.ResAttendanceDTO;
import kr.co.secondProject.login.entity.Attendance;
import kr.co.secondProject.login.repository.EmployeeRepository;
import kr.co.secondProject.vacation.dto.ResVacationDTO;
import kr.co.secondProject.vacation.entity.Vacation;
import kr.co.secondProject.vacation.repository.VacationRepository;
import lombok.RequiredArgsConstructor;

@Service  // 빈 등록 
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService{
	
	private final VacationRepository vacationrepository;
	private final EmployeeRepository employeeRepository;
	
	
	// 특정 직원 이번 달 휴가 신청 통계 조회
	
	
	
	// 특정 직원 휴가 신청 목록 조회
	public List<ResVacationDTO> getVacationList(Long employeeId){
		List<Vacation> list = vacationrepository.finByEmployee_IdOrderByDateDesc(Long employeeId);
		
		List<ResVacationDTO> result = list.stream()
										  .map(this::toResDto)
										  .collect(Collectors.toList());
		return result;
	}
	
	
	// Entity → ResAttendanceDTO 변환
    private ResVacationDTO toResDto(Vacation vacation) {
        ResVacationDTO dto = new ResVacationDTO();
        dto.setAnnualCode(vacation.getAnnualCode());
        dto.setStartTime(vacation.getStartTime());
        dto.setEndTime(vacation.getEndTime());
        dto.setRemaining(vacation.getRemaining());
        dto.setKind(vacation.getKind());
 
        if (vacation.getEmployeeId() != null) {
            dto.setEmployeeId(vacation.getEmployeeId().getId());
            dto.setEmployeeName(vacation.getEmployeeId().getName());
        }
        return dto;
    }
}
