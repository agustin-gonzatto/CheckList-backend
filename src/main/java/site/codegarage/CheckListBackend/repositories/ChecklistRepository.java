package site.codegarage.CheckListBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.codegarage.CheckListBackend.dtos.ChecklistRequestDTO;
import site.codegarage.CheckListBackend.dtos.ChecklistResponseDTO;
import site.codegarage.CheckListBackend.entities.Checklist;
import site.codegarage.CheckListBackend.entities.Dashboard;

import java.util.List;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    List<Checklist> findByDashboard(Dashboard dashboard);
}