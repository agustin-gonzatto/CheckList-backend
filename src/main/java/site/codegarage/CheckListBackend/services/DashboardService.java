package site.codegarage.CheckListBackend.services;

import org.springframework.stereotype.Service;
import site.codegarage.CheckListBackend.dtos.DashboardRequestDTO;
import site.codegarage.CheckListBackend.dtos.DashboardResponseDTO;
import site.codegarage.CheckListBackend.entities.Dashboard;
import site.codegarage.CheckListBackend.entities.User;
import site.codegarage.CheckListBackend.repositories.DashboardRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public DashboardResponseDTO createDashboard(DashboardRequestDTO dashboardDTO, User user) {
        Dashboard newDashboard = new Dashboard();
        newDashboard.setTitle(dashboardDTO.title());
        newDashboard.setOwner(user);

        LocalDateTime now = LocalDateTime.now();
        newDashboard.setCreatedAt(now);
        newDashboard.setModifiedAt(now);

        Dashboard savedDashboard = dashboardRepository.save(newDashboard);
        return convertToDTO(savedDashboard);
    }

    public List<DashboardResponseDTO> getUserDashboards(User user) {
        List<Dashboard> dashboards = dashboardRepository.findByOwner(user);
        return dashboards.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public void deleteDashboard(Long dashboardId, User user) {
        Dashboard dashboard = dashboardRepository.findById(dashboardId)
                .orElseThrow(() -> new RuntimeException("Dashboard no encontrado"));

        if (!dashboard.getOwner().equals(user)) {
            throw new RuntimeException("No autorizado para eliminar este dashboard");
        }

        dashboardRepository.delete(dashboard);
    }

    private DashboardResponseDTO convertToDTO(Dashboard dashboard) {
        return new DashboardResponseDTO(
                dashboard.getId(),
                dashboard.getTitle(),
                dashboard.getCreatedAt(),
                dashboard.getModifiedAt(),
                dashboard.getOwner().getUsername()
        );
    }
}