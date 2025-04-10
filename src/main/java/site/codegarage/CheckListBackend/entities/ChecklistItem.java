package site.codegarage.CheckListBackend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private boolean isChecked;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;
}