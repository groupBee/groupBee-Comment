package groupbee.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_gen")
    @SequenceGenerator(name = "comment_id_gen", sequenceName = "comment_num_seq", allocationSize = 1)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Size(max = 255)
    @NotNull
    @Column(name = "member_id", nullable = false)
    private String memberId;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @NotNull
    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

}