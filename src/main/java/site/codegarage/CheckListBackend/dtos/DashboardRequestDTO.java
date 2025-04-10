package site.codegarage.CheckListBackend.dtos;

import jakarta.validation.constraints.NotBlank;

public record DashboardRequestDTO(
        @NotBlank String title
) {}