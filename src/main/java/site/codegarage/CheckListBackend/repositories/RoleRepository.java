package site.codegarage.CheckListBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.codegarage.CheckListBackend.entities.Role;
import site.codegarage.CheckListBackend.entities.User;
import site.codegarage.CheckListBackend.enums.RoleList;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(RoleList name);
}
