package site.codegarage.CheckListBackend.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.codegarage.CheckListBackend.entities.Checklist;
import site.codegarage.CheckListBackend.entities.Dashboard;
import site.codegarage.CheckListBackend.entities.User;
import site.codegarage.CheckListBackend.repositories.ChecklistRepository;
import site.codegarage.CheckListBackend.repositories.DashboardRepository;

import java.time.LocalDateTime;

@Service
public class ChecklistService {

    @Autowired
    public DashboardRepository dashboardRepository;
    public ChecklistRepository checklistRepository;

    @Transactional
    public Checklist createChecklist(Checklist checklist, Long dashboardId, User user) {
        Dashboard dashboard = dashboardRepository.findByIdAndOwner(dashboardId, user)
                .orElseThrow(() -> new Error("Dashboard no encontrado"));

        checklist.setDashboard(dashboard);
        checklist.setModifiedAt(LocalDateTime.now());
        return checklistRepository.save(checklist);
    }
}