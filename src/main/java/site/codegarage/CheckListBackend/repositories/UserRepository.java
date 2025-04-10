package site.codegarage.CheckListBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.codegarage.CheckListBackend.entities.User;
import site.codegarage.CheckListBackend.enums.RoleList;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
