package site.codegarage.CheckListBackend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

public record ChecklistRequestDTO(
        @NotBlank(message = "El título es obligatorio")
        String title,

        String description,

        @NotNull(message = "La fecha de inicio es requerida")
        LocalDateTime startDate,

        @NotNull(message = "El dashboard es requerido")
        Long dashboardId,

        @Valid
        Set<ChecklistItemRequestDTO> items,

        Set<String> tags
) {}
