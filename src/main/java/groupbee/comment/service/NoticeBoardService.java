package groupbee.comment.service;

import groupbee.comment.dto.NoticeBoardDto;
import groupbee.comment.entity.NoticeBoardEntity;
import groupbee.comment.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;

    public List<NoticeBoardDto> getAllNoticeBoards() {
        List<NoticeBoardEntity> entities = noticeBoardRepository.findAll();
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NoticeBoardDto getNoticeBoard(Long id) {
        NoticeBoardEntity entity = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice board not found with id: " + id));
        return convertToDTO(entity);
    }
    public List<NoticeBoardDto> getNoticeboardsByTitle(String title) {
        List<NoticeBoardEntity> entities = noticeBoardRepository.findByTitle(title);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NoticeBoardDto createNoticeBoard(NoticeBoardDto dto) {
        NoticeBoardEntity entity = convertToEntity(dto);
        NoticeBoardEntity savedEntity = noticeBoardRepository.save(entity);
        return convertToDTO(savedEntity);
    }
    public NoticeBoardDto updateNoticeBoard(Long id, NoticeBoardDto dto) {
        NoticeBoardEntity entity = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice board not found with id: " + id));

        entity.setWriter(dto.getWriter());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setFileName(dto.getFileName());
        entity.setFile(dto.getFile());
        entity.setReadcount(dto.getReadcount());
        entity.setMustRead(dto.getMustRead());
        entity.setTempStorage(dto.getTempStorage());
        entity.setUpdate(dto.getUpdate());
        entity.setMemberId(dto.getMemberId());
        entity.setMustMustRead(dto.getMustMustRead());

        NoticeBoardEntity updatedEntity = noticeBoardRepository.save(entity);
        return convertToDTO(updatedEntity);
    }

    public void deleteNoticeBoard(Long id) {
        noticeBoardRepository.deleteById(id);
    }

    private NoticeBoardDto convertToDTO(NoticeBoardEntity entity) {
        NoticeBoardDto dto = new NoticeBoardDto();
        dto.setId(entity.getId());
        dto.setWriter(entity.getWriter());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setFileName(entity.getFileName());
        dto.setFile(entity.getFile());
        dto.setReadcount(entity.getReadcount());
        dto.setMustRead(entity.getMustRead());
        dto.setTempStorage(entity.getTempStorage());
        dto.setCreate(entity.getCreate());
        dto.setUpdate(entity.getUpdate());
        dto.setMemberId(entity.getMemberId());
        dto.setMustMustRead(entity.getMustMustRead());
        return dto;
    }

    private NoticeBoardEntity convertToEntity(NoticeBoardDto dto) {
        NoticeBoardEntity entity = new NoticeBoardEntity();
        entity.setId(dto.getId());
        entity.setWriter(dto.getWriter());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setFileName(dto.getFileName());
        entity.setFile(dto.getFile());
        entity.setReadcount(dto.getReadcount());
        entity.setMustRead(dto.getMustRead());
        entity.setTempStorage(dto.getTempStorage());
        entity.setCreate(dto.getCreate());
        entity.setUpdate(dto.getUpdate());
        entity.setMemberId(dto.getMemberId());
        entity.setMustMustRead(dto.getMustMustRead());
        return entity;
    }
}
