package groupbee.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "board_reply")
public class BoardReplyEntity {
    private Long id;

    private String comment;

    private LocalDate create;

    private BoardEntity noticeBoard;

    private LocalDate update;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @Column(name = "comment", nullable = false, length = Integer.MAX_VALUE)
    public String getComment() {
        return comment;
    }

    @ColumnDefault("now()")
    @Column(name = "\"create\"")
    public LocalDate getCreate() {
        return create;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "notice_board_id", nullable = false)
    public BoardEntity getNoticeBoard() {
        return noticeBoard;
    }

    @ColumnDefault("now()")
    @Column(name = "update")
    public LocalDate getUpdate() {
        return update;
    }

}