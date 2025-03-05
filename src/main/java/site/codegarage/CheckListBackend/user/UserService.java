package site.codegarage.CheckListBackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> newUser(User user){
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public ResponseEntity<Object> updateUser(Integer id, User updatedUser){
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        User existingUser = userOptional.get();
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setLastname(updatedUser.getLastname());
        existingUser.setName(updatedUser.getName());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setUsername(updatedUser.getUsername());
        userRepository.save(existingUser);
        return ResponseEntity.ok(existingUser);
    }

    public ResponseEntity<Object> deleteUser(Integer id){
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> getUserById(Integer id){
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        User user= userOptional.get();
        return ResponseEntity.ok(user);
    }

}
