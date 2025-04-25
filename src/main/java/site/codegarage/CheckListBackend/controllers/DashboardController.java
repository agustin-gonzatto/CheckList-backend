package site.codegarage.CheckListBackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.codegarage.CheckListBackend.dtos.DashboardRequestDTO;
import site.codegarage.CheckListBackend.dtos.DashboardResponseDTO;
import site.codegarage.CheckListBackend.entities.User;
import site.codegarage.CheckListBackend.services.DashboardService;
import site.codegarage.CheckListBackend.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/dashboards")
public class DashboardController {

    private final UserService userService;
    private final DashboardService dashboardService;

    public DashboardController(UserService userService, DashboardService dashboardService) {
        this.userService = userService;
        this.dashboardService = dashboardService;
    }

    @PostMapping
    public ResponseEntity<DashboardResponseDTO> createDashboard(
            @Valid @RequestBody DashboardRequestDTO dashboardDTO,
            Principal principal) {

        User user = userService.findByUsername(principal.getName());
        DashboardResponseDTO responseDTO = dashboardService.createDashboard(dashboardDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<DashboardResponseDTO>> getUserDashboards(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<DashboardResponseDTO> response = dashboardService.getUserDashboards(user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{dashboardId}")
    public ResponseEntity<Void> deleteDashboard(
            @PathVariable Long dashboardId,
            Principal principal) {

        User user = userService.findByUsername(principal.getName());
        dashboardService.deleteDashboard(dashboardId, user);
        return ResponseEntity.noContent().build();
    }
}