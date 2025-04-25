package site.codegarage.CheckListBackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.codegarage.CheckListBackend.dtos.ChecklistItemRequestDTO;
import site.codegarage.CheckListBackend.dtos.ChecklistItemResponseDTO;
import site.codegarage.CheckListBackend.services.ChecklistItemService;

import java.util.List;

@RestController
@RequestMapping("/api/checklists/{checklistId}/items")
public class ChecklistItemController {

    private final ChecklistItemService checklistItemService;

    public ChecklistItemController(ChecklistItemService checklistItemService) {
        this.checklistItemService = checklistItemService;
    }

    @PostMapping
    public ResponseEntity<ChecklistItemResponseDTO> createItem(
            @PathVariable Long checklistId,
            @Valid @RequestBody ChecklistItemRequestDTO itemDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(checklistItemService.createItem(checklistId, itemDTO));
    }

    @GetMapping
    public ResponseEntity<List<ChecklistItemResponseDTO>> getAllItemsByChecklist(
            @PathVariable Long checklistId) {
        return ResponseEntity.ok(checklistItemService.getAllItemsByChecklist(checklistId));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ChecklistItemResponseDTO> getItemById(
            @PathVariable Long checklistId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(checklistItemService.getItemById(checklistId, itemId));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ChecklistItemResponseDTO> updateItemf(
            @PathVariable Long checklistId,
            @PathVariable Long itemId,
            @Valid @RequestBody ChecklistItemRequestDTO itemDTO) {
        return ResponseEntity.ok(checklistItemService.updateItem(checklistId, itemId, itemDTO));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ChecklistItemResponseDTO> updateItem(
            @PathVariable Long checklistId,
            @PathVariable Long itemId,
            @Valid @RequestBody ChecklistItemRequestDTO itemDTO) {
        return ResponseEntity.ok(checklistItemService.updateItem(checklistId, itemId, itemDTO));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long checklistId,
            @PathVariable Long itemId) {
        checklistItemService.deleteItem(checklistId, itemId);
        return ResponseEntity.noContent().build();
    }
}
