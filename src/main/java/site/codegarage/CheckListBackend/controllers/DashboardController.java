package site.codegarage.CheckListBackend.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.codegarage.CheckListBackend.dtos.DashboardResponseDTO;
import site.codegarage.CheckListBackend.entities.Dashboard;
import site.codegarage.CheckListBackend.entities.User;
import site.codegarage.CheckListBackend.repositories.DashboardRepository;
import site.codegarage.CheckListBackend.repositories.UserRepository;
import site.codegarage.CheckListBackend.services.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/dashboards")
public class DashboardController {

    private final UserService userService;
    private final DashboardRepository dashboardRepository;

    // Constructor con inyección de dependencias
    public DashboardController(UserService userService,
                               DashboardRepository dashboardRepository) {
        this.userService = userService;
        this.dashboardRepository = dashboardRepository;
    }

    @PostMapping
    public ResponseEntity<DashboardResponseDTO> createDashboard(
            @Valid @RequestBody DashboardRequestDTO dashboardDTO,
            Principal principal) {

        // Obtener usuario autenticado
        User user = userService.findByUsername(principal.getName());

        // Crear nueva entidad
        Dashboard newDashboard = new Dashboard();
        newDashboard.setTitle(dashboardDTO.title());
        newDashboard.setOwner(user);

        // Establecer fechas
        LocalDateTime now = LocalDateTime.now();
        newDashboard.setCreatedAt(now);
        newDashboard.setModifiedAt(now);

        // Guardar en BD
        Dashboard savedDashboard = dashboardRepository.save(newDashboard);

        // Convertir a DTO y retornar respuesta
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapToDashboardResponseDTO(savedDashboard));
    }

    @GetMapping
    public ResponseEntity<List<DashboardResponseDTO>> getUserDashboards(Principal principal) {
        // Obtener dashboards del usuario
        User user = userService.findByUsername(principal.getName());
        List<Dashboard> dashboards = dashboardRepository.findByOwner(user);

        // Convertir a DTOs
        List<DashboardResponseDTO> response = dashboards.stream()
                .map(this::mapToDashboardResponseDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    private DashboardResponseDTO mapToDashboardResponseDTO(Dashboard dashboard) {
        return new DashboardResponseDTO(
                dashboard.getId(),
                dashboard.getTitle(),
                dashboard.getCreatedAt(),
                dashboard.getModifiedAt(),
                dashboard.getOwner().getUsername() // Agregar owner si es necesario
        );
    }

    // Definir DTOs como records (Java 16+)
    public record DashboardRequestDTO(
            @NotBlank(message = "El título es obligatorio")
            String title
    ) {}

    public record DashboardResponseDTO(
            Long id,
            String title,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            String owner
    ) {}
}