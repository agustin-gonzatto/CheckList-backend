package site.codegarage.CheckListBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL)
    private List<ChecklistItem> items = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "checklist_tags", joinColumns = @JoinColumn(name = "checklist_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();
}