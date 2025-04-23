package site.codegarage.CheckListBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import site.codegarage.CheckListBackend.entities.ChecklistItem;
import java.util.List;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
    List<ChecklistItem> findByChecklistId(Long checklistId);
}