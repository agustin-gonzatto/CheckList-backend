package site.codegarage.CheckListBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.codegarage.CheckListBackend.entities.Dashboard;
import site.codegarage.CheckListBackend.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
    List<Dashboard> findByOwner(User user);

    Optional<Dashboard> findByIdAndOwner(Long dashboardId, User user);
}