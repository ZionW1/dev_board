package com.aloha.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.community.domain.Comments;
import com.aloha.community.service.CommentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;



@Slf4j
@Controller
@RequestMapping("/comment")
public class CommnetController {

    @Autowired
    private CommentService commentService;

    /**
     * 댓글 등록
     * @param comment
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("")
    public String commentPost(@RequestBody Comments comment) throws Exception{
        //TODO: process POST request
        int result = commentService.insert(comment);
        if(result > 0){
            return "SUCCESS";
        }
        return "FAIL";
    }

    /**
     * 댓글 조회
     * @param model
     * @param boardNo
     * @return
     * @throws Exception
     */
    @GetMapping("")
    public String commentList(Model model,@RequestParam("boardNo") int boardNo) throws Exception{
        List<Comments> commentList = commentService.listByParent(boardNo);
        model.addAttribute("commentList", commentList);
        return "/comment/list";
    }

    /**
     * 댓글 삭제
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/{id}")
    public String deleteCommnet(@PathVariable("id") String id) throws Exception{
        log.info("id : " + id);
        int result = commentService.delete(id);

        if(result > 0 ){
            return "SUCCESS";
        }
        return "FAIL";
    }

    /**
     * 댓글 수정
     * @param comment
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PutMapping("")
    public String updateComment(@RequestBody Comments comment) throws Exception {
        int result = commentService.update(comment);
        log.info("result : " + result);
        if(result > 0){
            return "SUCCESS";
        }
        return "FAIL";
    }
}
