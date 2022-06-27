package com.tencoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.blog.model.Board;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void write(Board board, User user) { // title, content
		board.setCount(0);
		board.setUserId(user);
		boardRepository.save(board);
	}
	
	public Page<Board> getBoardList(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	public Board boardDetail(int boardId) { // 매개변수 : where절 id값
		return boardRepository.findById(boardId).orElseThrow(() -> {
			return new IllegalArgumentException("해당글은 찾을 수 없습니다.");
		});
	}
}