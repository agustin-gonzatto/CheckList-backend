package site.codegarage.CheckListBackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.codegarage.CheckListBackend.dtos.ChecklistItemRequestDTO;
import site.codegarage.CheckListBackend.dtos.ChecklistRequestDTO;
import site.codegarage.CheckListBackend.dtos.ChecklistResponseDTO;
import site.codegarage.CheckListBackend.services.ChecklistService;

import java.util.List;

@RestController
@RequestMapping("/api/checklists")
public class ChecklistController {

    private final ChecklistService checklistService;

    public ChecklistController(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @PostMapping
    public ResponseEntity<ChecklistResponseDTO> createChecklist(
            @Valid @RequestBody ChecklistRequestDTO checklistDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(checklistService.createChecklist(checklistDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistResponseDTO> getChecklistById(@PathVariable Long id) {
        return ResponseEntity.ok(checklistService.getChecklistById(id));
    }

    @GetMapping("/dashboard/{dashboardId}")
    public ResponseEntity<List<ChecklistResponseDTO>> getChecklistsByDashboard(
            @PathVariable Long dashboardId) {
        return ResponseEntity.ok(checklistService.getChecklistsByDashboard(dashboardId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChecklistResponseDTO> updateChecklist(
            @PathVariable Long id,
            @Valid @RequestBody ChecklistRequestDTO checklistDTO) {
        return ResponseEntity.ok(checklistService.updateChecklist(id, checklistDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChecklist(@PathVariable Long id) {
        checklistService.deleteChecklist(id);
        return ResponseEntity.noContent().build();
    }
}