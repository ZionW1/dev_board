package com.aloha.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.aloha.community.domain.Board;
import com.aloha.community.domain.Files;
import com.aloha.community.domain.Option;
import com.aloha.community.domain.Page;
import com.aloha.community.service.BoardService;
import com.aloha.community.service.FileService;

import lombok.extern.slf4j.Slf4j;

/**
 * 목록         /board/list [Get]
 * 조회         /board/select [Get]
 * 등록         /board/insert[Get]
 * 등록 처리    /board/insert [Post]
 * 수정         /board/update [Get]
 * 수정 처리    /board/update [Post]
 * 삭제 처리    /board/delete [Post]
 */

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;

    /**
     * 목록
     * 
     * @param model
     * @return
     * @throws Exception
     */

    @GetMapping("/list")
    public String list(Model model, 
                // @RequestParam(name="keyword", defaultValue = "") String keyword
                Option option, 
                //@RequestParam(name="rows", defaultValue = "10") int rows
                Page page
                ) throws Exception {
        List<Board> boardList = boardService.list(option, page);

        log.info("page.getPage : " + page.getPage());
        log.info("page.getTotal : " + page.getTotal());
        log.info("page.getCount : " + page.getCount());
        log.info("page.getStart : " + page.getStart());
        log.info("page.getEnd : " + page.getEnd());
        
        model.addAttribute("boardList", boardList);
        model.addAttribute("option", option);
        model.addAttribute("rows", page.getRows());
        model.addAttribute("page", page);

        String pageUrl = UriComponentsBuilder.fromPath("/board/list")
                        // .queryParam("page", page.getPage())
                        .queryParam("keyword", option.getKeyword())
                        .queryParam("code", option.getCode())
                        .queryParam("rows", page.getRows())
                        .queryParam("orderCode", option.getOrderCode())
                        .build()
                        .toUriString();
        model.addAttribute("pageUrl", pageUrl);

        return "/board/list";
    }
    
    // 조회
    @GetMapping("/select")
    public String select(Model model, @RequestParam("id") String id, Files file) throws Exception{
        //게시글 조회
        Board board = boardService.select(id);
        model.addAttribute("board", board);
        log.info("no = " + board.getNo());

        // 파일 목록 조회
        file.setParentNo(board.getNo());
        file.setParentTable("board");

        log.info("file : " + file);
        List<Files> fileList = fileService.listByParent(file);
        model.addAttribute("fileList", fileList);
        return "/board/select";
    }
    
    // 등록
    @GetMapping("/insert")
    public String insert() {
        return "/board/insert";
    }
    
    // 등록 처리
    @PostMapping("/insert")
    public String insertPost(Board board) throws Exception {
        log.info("board : " + board);
        int result = boardService.insert(board);
        if(result > 0){
            return "redirect:/board/list";
        }
        return "redirect:/board/insert?error";
    }
    
    // 수정
    @GetMapping("/update")
    public String update(Model model, @RequestParam("id") String id, Files file) throws Exception {
        // 게시글 조회
        Board board = boardService.select(id);
        model.addAttribute("board", board);

        log.info("no = " + board.getNo());
        // 파일 목록 조회
        file.setParentNo(board.getNo());
        file.setParentTable("board");

        log.info("file : " + file);
        List<Files> fileList = fileService.listByParent(file);
        model.addAttribute("fileList", fileList);

        return "/board/update";
    }

    // 수정 처리
    @PostMapping("/update")
    public String updatePost(Board board) throws Exception{
        int result = boardService.update(board);
        if(result > 0){
            return "redirect:/board/list";
        }
        return "redirect:/board/update?error&id="+board.getId();
    }
    // 삭제 처리
    @PostMapping("/delete")
    public String delete(@RequestParam("id") String id) throws Exception{
        int result = boardService.delete(id);
        if(result > 0){
            return "redirect:/board/list";
        }
        return "redirect:/board/update?error&id="+id;
        
    }
    
}
