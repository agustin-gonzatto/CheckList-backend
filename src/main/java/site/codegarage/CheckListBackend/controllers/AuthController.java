package site.codegarage.CheckListBackend.controllers;


import jakarta.validation.Valid;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.codegarage.CheckListBackend.dtos.LoginUserDto;
import site.codegarage.CheckListBackend.dtos.NewUserDto;
import site.codegarage.CheckListBackend.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Revise sus credenciales");
        }
        try {
            String jwt = authService.authenticate(loginUserDto.username, loginUserDto.getPassword());
            return ResponseEntity.ok(jwt);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Revise sus credenciales" + e);
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
