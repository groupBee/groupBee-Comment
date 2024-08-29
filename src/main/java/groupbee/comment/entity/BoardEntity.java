package groupbee.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_id_gen")
    @SequenceGenerator(name = "board_id_gen", sequenceName = "board_num_seq", allocationSize = 1)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDate createDate;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "read_count", nullable = false)
    private Long readCount;

    @NotNull
    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @NotNull
    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    private String content;

    @Size(max = 255)
    @Column(name = "file")
    private String file;

    @Size(max = 255)
    @NotNull
    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Size(max = 255)
    @Column(name = "original_file")
    private String originalFile;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "must_read")
    private Boolean mustRead;

    @Column(name = "must_must_read")
    private Boolean mustMustRead;

}