package site.codegarage.CheckListBackend.dtos;

import java.time.LocalDateTime;

public record DashboardResponseDTO(
        Long id,
        String title,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}