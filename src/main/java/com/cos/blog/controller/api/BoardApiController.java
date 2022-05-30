package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.Message;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/board")
@RestController
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Message> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.save(board, principal.getUser());
        return new Message<>(HttpStatus.OK, 1)
                .asResponseEntity();
    }

    @PutMapping("{id}")
    public ResponseEntity<Message> update(@PathVariable int id, @RequestBody Board board){
        boardService.update(id, board);
        return new Message<>(HttpStatus.OK, 1)
                .asResponseEntity();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteById(@PathVariable int id){
        boardService.deleteById(id);
        return new Message<>(HttpStatus.NO_CONTENT, 1)
                .asResponseEntity();
    }

    @PostMapping("{boardId}/reply")
    public ResponseEntity<Message> saveReply(@PathVariable int boardId,
                                             @RequestBody Reply reply,
                                             @AuthenticationPrincipal PrincipalDetail principal){
        boardService.saveReply(principal.getUser(), boardId, reply);
        return new Message<>(HttpStatus.OK, 1)
                .asResponseEntity();
    }

    @DeleteMapping("{boardId}/reply/{replyId}")
    public ResponseEntity<Message> deleteReply(@PathVariable int replyId){
        boardService.deleteReply(replyId);
        return new Message<>(HttpStatus.OK, 1)
                .asResponseEntity();
    }
}
