package site.codegarage.CheckListBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Getter
@Setter
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


    public boolean isCompleted() {
        return isChecked;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}