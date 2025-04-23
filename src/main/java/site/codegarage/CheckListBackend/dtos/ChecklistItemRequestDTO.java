package site.codegarage.CheckListBackend.dtos;

import jakarta.validation.constraints.NotBlank;

public record ChecklistItemRequestDTO(
        @NotBlank(message = "La descripción del ítem es obligatoria")
        String description,

        boolean completed
) {

}