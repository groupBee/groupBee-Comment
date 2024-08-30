package groupbee.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @CreationTimestamp
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "read_count", nullable = false)
    private Long readCount;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @NotNull
    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    private String content;

    @Size(max = 255)
    @Column(name = "file")
    private String file;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Size(max = 255)
    @Column(name = "original_file_name")
    private String originalFileName;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "must_read")
    private Boolean mustRead;

    @Column(name = "must_must_read")
    private Boolean mustMustRead;

    @OneToMany(mappedBy = "boardId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();
}