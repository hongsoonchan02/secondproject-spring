package kr.co.secondProject.dashboard.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.secondProject.dashboard.dto.DashboardResDTO;
import kr.co.secondProject.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name ="Dashboard", description = "대시보드 API")
public class DashbordController {

    private final DashboardService dashboardService;

    @GetMapping("/{employeeId}")
    @Operation(summary = "대시보드 조회", description = "직원 ID로 대시보드 전체 정보 조회")
    public ResponseEntity<DashboardResDTO> getDashboard(@PathVariable Long employeeId){
        return ResponseEntity.ok(dashboardService.getDashboardInfo(employeeId));
    }

    @PostMapping("/{employeeId}/checkin")
    @Operation(summary = "출근 처리", description = "출근 버튼 클릭 시 출근처리, 이미 출근한 경우 에러 반환")
    public ResponseEntity<Void> checkOUt(@PathVariable Long employeeId){
        dashboardService.checkOut(employeeId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{employeeId}/checkout")
    @Operation(summary = "퇴근처리", description = "퇴근 버튼 클릭 시 퇴근 시각 및 근무시간 기록")
    public ResponseEntity<Void> checkOut(@PathVariable Long employeeId) {
        dashboardService.checkOut(employeeId);
        return ResponseEntity.ok().build();
    }


}
