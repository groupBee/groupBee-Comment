package groupbee.comment.service.board;

import groupbee.comment.entity.BoardEntity;
import groupbee.comment.repository.BoardRepository;
import groupbee.comment.service.feign.EmployeeFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final EmployeeFeignClient employeeFeignClient;

    public BoardEntity getBoard() {
        Map<String, Object> userInfo = employeeFeignClient.getUserInfo();

        return boardRepository.findByMemberId((String) userInfo.get("id"));
    }
}
