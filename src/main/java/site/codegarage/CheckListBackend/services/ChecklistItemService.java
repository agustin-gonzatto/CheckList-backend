package site.codegarage.CheckListBackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.codegarage.CheckListBackend.dtos.ChecklistItemRequestDTO;
import site.codegarage.CheckListBackend.dtos.ChecklistItemResponseDTO;
import site.codegarage.CheckListBackend.entities.Checklist;
import site.codegarage.CheckListBackend.entities.ChecklistItem;
import site.codegarage.CheckListBackend.repositories.ChecklistItemRepository;
import site.codegarage.CheckListBackend.repositories.ChecklistRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {

    private final ChecklistItemRepository checklistItemRepository;
    private final ChecklistRepository checklistRepository;

    public ChecklistItemResponseDTO createItem(Long checklistId, ChecklistItemRequestDTO itemDTO) {
        Checklist checklist = checklistRepository.findById(checklistId)
                .orElseThrow(() -> new RuntimeException("Checklist no encontrado con id: " + checklistId));

        ChecklistItem newItem = new ChecklistItem();
        newItem.setDescription(itemDTO.description());
        newItem.setChecked(itemDTO.completed());
        newItem.setChecklist(checklist);

        ChecklistItem savedItem = checklistItemRepository.save(newItem);
        return mapToDTO(savedItem);
    }

    public List<ChecklistItemResponseDTO> getAllItemsByChecklist(Long checklistId) {
        List<ChecklistItem> items = checklistItemRepository.findByChecklistId(checklistId);
        return items.stream().map(this::mapToDTO).toList();
    }

    public ChecklistItemResponseDTO getItemById(Long checklistId, Long itemId) {
        ChecklistItem item = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado con id: " + itemId));

        if (!item.getChecklist().getId().equals(checklistId)) {
            throw new RuntimeException("El ítem no pertenece al checklist especificado");
        }

        return mapToDTO(item);
    }

    public ChecklistItemResponseDTO updateItem(Long checklistId, Long itemId, ChecklistItemRequestDTO itemDTO) {
        ChecklistItem existingItem = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado con id: " + itemId));

        if (!existingItem.getChecklist().getId().equals(checklistId)) {
            throw new RuntimeException("El ítem no pertenece al checklist especificado");
        }

        existingItem.setDescription(itemDTO.description());
        existingItem.setChecked(itemDTO.completed());

        ChecklistItem updatedItem = checklistItemRepository.save(existingItem);
        return mapToDTO(updatedItem);
    }

    public ChecklistItemResponseDTO updateItemPatch(Long checklistId, Long itemId, ChecklistItemRequestDTO itemDTO) {
        ChecklistItem existingItem = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado con id: " + itemId));

        if (!existingItem.getChecklist().getId().equals(checklistId)) {
            throw new RuntimeException("El ítem no pertenece al checklist especificado");
        }

        if (!itemDTO.description().isBlank()){
            existingItem.setDescription(itemDTO.description());
        }
        existingItem.setChecked(itemDTO.completed());
        ChecklistItem updatedItem = checklistItemRepository.save(existingItem);
        return mapToDTO(updatedItem);
    }

    public void deleteItem(Long checklistId, Long itemId) {
        ChecklistItem item = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado con id: " + itemId));

        if (!item.getChecklist().getId().equals(checklistId)) {
            throw new RuntimeException("El ítem no pertenece al checklist especificado");
        }

        checklistItemRepository.delete(item);
    }

    private ChecklistItemResponseDTO mapToDTO(ChecklistItem item) {
        return new ChecklistItemResponseDTO(
                item.getId(),
                item.getDescription(),
                item.isChecked(),
                item.getChecklist().getId(),
                item.getCreatedAt(),
                item.getModifiedAt()
        );
    }
}