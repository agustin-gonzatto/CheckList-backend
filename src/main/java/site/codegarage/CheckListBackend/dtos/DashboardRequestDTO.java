package site.codegarage.CheckListBackend.dtos;

import jakarta.validation.constraints.NotBlank;

public record DashboardRequestDTO(
        @NotBlank(message = "El título es obligatorio")
        String title
) {}