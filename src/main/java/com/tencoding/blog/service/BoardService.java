package com.tencoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.blog.dto.BoardSaveRequestDto;
import com.tencoding.blog.model.Board;
import com.tencoding.blog.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void write(BoardSaveRequestDto dto) {
		Board boardEntity = BoardSaveRequestDto.toEntity(dto);
		boardRepository.save(boardEntity);
	}
	
	@Transactional
	public Page<Board> seeList(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional
	public Board seeDetail(int id) {
		Board board = boardRepository.mFindById(id).orElseThrow(() -> {
			return new RuntimeException("해당글은 삭제되었습니다");
		});
		
		// 더티체킹 = 조회수증가
		board.setCount(board.getCount() + 1);
		
		return board;
	}
	
	@Transactional
	public void update(int id, BoardSaveRequestDto dto) {
		// 가져오기
		Board boardEntity = boardRepository.findById(id).orElseThrow(() -> {
			return new RuntimeException("해당글은 없는 데이터입니다");
		});
		
		boardEntity.setTitle(dto.getTitle());
		boardEntity.setContent(dto.getContent());
	}
	
	@Transactional
	public int delete(int id) {
		return boardRepository.mDeleteById(id);
	}
}
