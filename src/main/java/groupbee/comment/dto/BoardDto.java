package groupbee.comment.dto;

import groupbee.comment.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private BoardEntity board;
    private Long commentCount;
}
