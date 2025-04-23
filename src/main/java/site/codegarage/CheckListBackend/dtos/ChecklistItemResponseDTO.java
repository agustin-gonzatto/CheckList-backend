package site.codegarage.CheckListBackend.dtos;

import java.time.LocalDateTime;

public record ChecklistItemResponseDTO(
        Long id,
        String description,
        boolean completed,
        Long checklistId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}