package site.codegarage.CheckListBackend.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public record ChecklistResponseDTO(
        Long id,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime modifiedAt,
        String dashboardTitle,
        Set<ChecklistItemResponseDTO> items,
        Set<String> tags
) {}
