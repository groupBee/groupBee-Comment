package groupbee.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "notice_board")
public class NoticeBoardEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "writer", nullable = false, length = Integer.MAX_VALUE)
    private String writer;

    @NotNull
    @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
    private String title;

    @NotNull
    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "file_name", length = Integer.MAX_VALUE)
    private String fileName;

    @Column(name = "file", length = Integer.MAX_VALUE)
    private String file;

    @Column(name = "readcount")
    private Integer readcount;

    @Column(name = "must_read")
    private Boolean mustRead;

    @Column(name = "temp_storage")
    private Boolean tempStorage;

    @Column(name = "\"create\"")
    private Instant create;

    @Column(name = "update")
    private Instant update;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "must_must_read")
    private Short mustMustRead;

}