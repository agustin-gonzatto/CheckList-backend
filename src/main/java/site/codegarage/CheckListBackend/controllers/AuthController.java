package site.codegarage.CheckListBackend.controllers;


import jakarta.validation.Valid;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.codegarage.CheckListBackend.dtos.LoginResponseDto;
import site.codegarage.CheckListBackend.dtos.LoginUserDto;
import site.codegarage.CheckListBackend.dtos.NewUserDto;
import site.codegarage.CheckListBackend.entities.User;
import site.codegarage.CheckListBackend.services.AuthService;
import site.codegarage.CheckListBackend.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private  final  UserService userService;

    public AuthController(AuthService authService, UserService userService){
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(null);
        }
        try {
            String jwt = authService.authenticate(loginUserDto.username, loginUserDto.getPassword());
            User user = userService.findByUsername(loginUserDto.getUsername());
            LoginResponseDto responseDto = new LoginResponseDto(
                    jwt,
                    user.getUsername(), // Asegúrate de que tu entidad User tenga getUsername()
                    user.getEmail() // Asegúrate de que tu entidad User tenga getEmail() si lo incluyes
            );

            return ResponseEntity.ok(responseDto);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Revise los campos");
        }
        try {

            authService.registerUser(newUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registrado");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkAuth(){
        return ResponseEntity.ok().body("logeado");
    }


}
