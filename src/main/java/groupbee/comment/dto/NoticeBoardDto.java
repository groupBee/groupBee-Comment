package groupbee.comment.dto;

import java.time.Instant;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeBoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String fileName;
    private String file;
    private Integer readcount;
    private Boolean mustRead;
    private Boolean tempStorage;
    private Instant create;
    private Instant update;
    private Integer memberId;
    private Short mustMustRead;
}