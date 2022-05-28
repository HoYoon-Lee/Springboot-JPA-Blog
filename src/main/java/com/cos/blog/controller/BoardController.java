package com.cos.blog.controller;

import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("board")
@Controller
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("boards", boardService.list(pageable));
        return "index";
    }

    @GetMapping("writeForm")
    public String writeForm(){
        return "board/writeForm";
    }

    @GetMapping("{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model){
        model.addAttribute("board", boardService.postById(id));
        return "board/updateForm";
    }

    @GetMapping("{id}")
    public String findById(@PathVariable int id, Model model){
        model.addAttribute("board", boardService.postById(id));
        return "board/detail";
    }
}
