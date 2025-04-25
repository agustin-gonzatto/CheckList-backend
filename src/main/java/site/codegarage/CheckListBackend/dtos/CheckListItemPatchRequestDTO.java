package site.codegarage.CheckListBackend.dtos;


public record CheckListItemPatchRequestDTO (

        String description,
        boolean completed
){

}
