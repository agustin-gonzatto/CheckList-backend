package site.codegarage.CheckListBackend.dtos;

public record LoginResponseDto(
         String token,
         String username,
         String email
) {
}
