package site.codegarage.CheckListBackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

    public String username;
    public String password;
    public String name;
    public String lastname;
    public String email;

}
