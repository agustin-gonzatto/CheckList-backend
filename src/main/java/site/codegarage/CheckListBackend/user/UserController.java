package site.codegarage.CheckListBackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        return userService.newUser(user);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Object> updateUserById(@PathVariable Integer id, @RequestBody User updatedUser){
        return this.userService.updateUser(id,updatedUser);
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Object> deleteUserById(@PathVariable Integer id){
        return this.userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Object> getUserById(@PathVariable Integer id){
        return this.userService.getUserById(id);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();  // Obtienes la lista de usuarios desde el servicio
        return ResponseEntity.ok(users);  // Spring convierte automáticamente la lista de usuarios en JSON
    }
}
