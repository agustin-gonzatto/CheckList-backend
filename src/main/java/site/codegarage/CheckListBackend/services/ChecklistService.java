package site.codegarage.CheckListBackend.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.codegarage.CheckListBackend.dtos.ChecklistItemRequestDTO;
import site.codegarage.CheckListBackend.dtos.ChecklistItemResponseDTO;
import site.codegarage.CheckListBackend.dtos.ChecklistRequestDTO;
import site.codegarage.CheckListBackend.dtos.ChecklistResponseDTO;
import site.codegarage.CheckListBackend.entities.Checklist;
import site.codegarage.CheckListBackend.entities.ChecklistItem;
import site.codegarage.CheckListBackend.entities.Dashboard;
import site.codegarage.CheckListBackend.entities.User;
import site.codegarage.CheckListBackend.repositories.ChecklistRepository;
import site.codegarage.CheckListBackend.repositories.DashboardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final DashboardRepository dashboardRepository;

    public ChecklistService(ChecklistRepository checklistRepository,
                            DashboardRepository dashboardRepository) {
        this.checklistRepository = checklistRepository;
        this.dashboardRepository = dashboardRepository;
    }

    // Métodos de conversión
    private ChecklistResponseDTO convertToDTO(Checklist checklist) {
        return new ChecklistResponseDTO(
                checklist.getId(),
                checklist.getTitle(),
                checklist.getDescription(),
                checklist.getStartDate(),
                checklist.getModifiedAt(),
                checklist.getDashboard().getTitle(),
                checklist.getItems().stream()
                        .map(this::convertItemToDTO)
                        .collect(Collectors.toSet()),
                checklist.getTags()
        );
    }

    private ChecklistItemResponseDTO convertItemToDTO(ChecklistItem item) {
        return new ChecklistItemResponseDTO(
                item.getId(),
                item.getDescription(),
                item.isCompleted(),
                item.getChecklist().getId(),
                item.getCreatedAt(),
                item.getModifiedAt()
        );
    }

    private Checklist convertToEntity(ChecklistRequestDTO dto) {
        Dashboard dashboard = dashboardRepository.findById(dto.dashboardId())
                .orElseThrow(() -> new RuntimeException("Dashboard no encontrado"));

        Checklist checklist = new Checklist();
        checklist.setTitle(dto.title());
        checklist.setDescription(dto.description());
        checklist.setStartDate(dto.startDate());
        checklist.setTags(dto.tags());
        checklist.setDashboard(dashboard);

        if (dto.items() != null) {
            checklist.setItems(
                    dto.items().stream()
                            .map(this::convertItemToEntity)
                            .peek(item -> ((ChecklistItem) item).setChecklist(checklist))
                            .collect(Collectors.toList())
            );
        }

        return checklist;
    }

    private ChecklistItem convertItemToEntity(ChecklistItemRequestDTO itemDTO) {
        ChecklistItem item = new ChecklistItem();
        item.setDescription(itemDTO.description());
        item.setChecked(itemDTO.completed());
        return item;
    }

    // Métodos del servicio
    public ChecklistResponseDTO createChecklist(ChecklistRequestDTO checklistDTO) {
        Checklist checklist = convertToEntity(checklistDTO);
        Checklist savedChecklist = checklistRepository.save(checklist);
        return convertToDTO(savedChecklist);
    }

    public ChecklistResponseDTO getChecklistById(Long id) {
        Checklist checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checklist no encontrada"));
        return convertToDTO(checklist);
    }

    public List<ChecklistResponseDTO> getChecklistsByDashboard(Long dashboardId) {
        Dashboard dashboard = dashboardRepository.findById(dashboardId)
                .orElseThrow(() -> new RuntimeException("Dashboard no encontrado"));

        return checklistRepository.findByDashboard(dashboard).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ChecklistResponseDTO updateChecklist(Long id, ChecklistRequestDTO checklistDTO) {
        Checklist existingChecklist = checklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checklist no encontrada"));

        // Actualizar campos
        existingChecklist.setTitle(checklistDTO.title());
        existingChecklist.setDescription(checklistDTO.description());
        existingChecklist.setStartDate(checklistDTO.startDate());
        existingChecklist.setTags(checklistDTO.tags());

        // Actualizar items
        existingChecklist.getItems().clear();
        existingChecklist.getItems().addAll(
                checklistDTO.items().stream()
                        .map(this::convertItemToEntity)
                        .peek(item -> item.setChecklist(existingChecklist))
                        .toList()
        );

        Checklist updatedChecklist = checklistRepository.save(existingChecklist);
        return convertToDTO(updatedChecklist);
    }

    public ChecklistResponseDTO addItemToChecklist(Long checklistId, ChecklistItemRequestDTO itemDTO) {
        Checklist checklist = checklistRepository.findById(checklistId)
                .orElseThrow(() -> new RuntimeException("Checklist no encontrada"));

        ChecklistItem newItem = new ChecklistItem();
        newItem.setDescription(itemDTO.description());
        newItem.setChecked(itemDTO.completed());
        newItem.setChecklist(checklist);

        checklist.getItems().add(newItem);
        checklist.setModifiedAt(LocalDateTime.now());

        Checklist updatedChecklist = checklistRepository.save(checklist);
        return convertToDTO(updatedChecklist);
    }


    public void deleteChecklist(Long id) {
        checklistRepository.deleteById(id);
    }
}