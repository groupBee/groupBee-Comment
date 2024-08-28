package groupbee.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
    private Long id;

    private String content;

    private LocalDate create;

    private String file;

    private String fileName;

    private String memberId;

    private Short mustMustRead;

    private Boolean mustRead;

    private Integer readcount;

    private Boolean tempStorage;

    private String title;

    private LocalDate update;

    private String writer;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    public String getContent() {
        return content;
    }

    @ColumnDefault("now()")
    @Column(name = "\"create\"")
    public LocalDate getCreate() {
        return create;
    }

    @Column(name = "file", length = Integer.MAX_VALUE)
    public String getFile() {
        return file;
    }

    @Column(name = "file_name", length = Integer.MAX_VALUE)
    public String getFileName() {
        return fileName;
    }

    @Column(name = "member_id")
    public String getMemberId() {
        return memberId;
    }

    @Column(name = "must_must_read")
    public Short getMustMustRead() {
        return mustMustRead;
    }

    @Column(name = "must_read")
    public Boolean getMustRead() {
        return mustRead;
    }

    @Column(name = "readcount")
    public Integer getReadcount() {
        return readcount;
    }

    @Column(name = "temp_storage")
    public Boolean getTempStorage() {
        return tempStorage;
    }

    @NotNull
    @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
    public String getTitle() {
        return title;
    }

    @ColumnDefault("now()")
    @Column(name = "update")
    public LocalDate getUpdate() {
        return update;
    }

    @NotNull
    @Column(name = "writer", nullable = false, length = Integer.MAX_VALUE)
    public String getWriter() {
        return writer;
    }

}