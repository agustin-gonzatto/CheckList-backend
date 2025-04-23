package site.codegarage.CheckListBackend.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.codegarage.CheckListBackend.dtos.NewUserDto;
import site.codegarage.CheckListBackend.entities.Role;
import site.codegarage.CheckListBackend.entities.User;
import site.codegarage.CheckListBackend.enums.RoleList;
import site.codegarage.CheckListBackend.jwt.JwtUtil;
import site.codegarage.CheckListBackend.repositories.RoleRepository;

@Service
public class AuthService {

    private final UserService userService;
    private  final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthService(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public String authenticate(String username, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return  jwtUtil.generateToken(authResult);
    }

    public void registerUser(NewUserDto newUserDto){
        if (userService.existByUsername(newUserDto.getUsername())){
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        Role roleUser = roleRepository.findByName(RoleList.ROLE_USER).orElseThrow(()->new RuntimeException("Rol no encontrado"));
        User user = new User(roleUser, newUserDto.getName(), newUserDto.getLastname(), newUserDto.getUsername(), newUserDto.getEmail(), passwordEncoder.encode(newUserDto.getPassword()));
        userService.save(user);

    }

}
