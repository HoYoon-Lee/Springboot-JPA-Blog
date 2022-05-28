package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void save(Board board, User user){
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> list(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board postById(int id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트를 찾을 수 없습니다. id: " + id));
    }

    @Transactional
    public void deleteById(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Board requestBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트를 찾을 수 없습니다. id: " + id));
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
    }
}
