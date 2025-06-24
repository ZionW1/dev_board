package com.aloha.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.community.domain.Comments;
import com.aloha.community.mapper.CommentMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService{
    
    @Autowired
    private CommentMapper commentMapper ;
    
    @Override
    public List<Comments> list() throws Exception {
        List<Comments> commentList = commentMapper.list();
        return commentList;
    }

    @Override
    public Comments select(String id) throws Exception {
        Comments comment = commentMapper.select(id);
        return comment;
    }

    @Override
    public int insert(Comments comment) throws Exception {
        // 답글 등록
        int result = commentMapper.insert(comment);
        
        // 댓글 등록
        // : 댓글 번호를 부모 댓글 번호로 수정(no = parent_no)
        int parentNo = comment.getParentNo();
        if(parentNo == 0) {
            comment.setParentNo(comment.getNo());
            commentMapper.update(comment);
        }

        return result;
    }

    @Override
    public int update(Comments comment) throws Exception {
        String id = comment.getId();
        String updatedWriter = comment.getWriter();
        String updatedContent = comment.getContent();
        comment = commentMapper.select(id); // parentNo 유지
        
        comment.setWriter(updatedWriter);
        comment.setContent(updatedContent);
        int result = commentMapper.update(comment);
        return result;
        /*
        String id = comment.getId();
        log.info("================= " + id);
        Comments commentWC = commentMapper.select(id); // parentNo 유지
        commentWC.setWriter(comment.getWriter());
        commentWC.setContent(comment.getContent());
        log.info("================= " + commentWC);
        int result = commentMapper.update(commentWC);
        return result;
        */
    }

    @Override
    public int delete(String id) throws Exception {
        int result = 0;
        Comments deleteComment = commentMapper.select(id);

        int parentNo = deleteComment.getNo();
        int no = deleteComment.getParentNo();
        // 답글 + 댓글 삭제
        if(no == parentNo){
            result = commentMapper.deleteReplyByParent(parentNo);
        }else{
            // 댓글 삭제
            result = commentMapper.delete(id);
        }

        return result;
    }

    @Override
    public List<Comments> listByParent(int boardNo) throws Exception {
        List<Comments> commentList = commentMapper.listByParent(boardNo);
        return commentList;
    }

    @Override
    public int deleteByParent(int boardNo) throws Exception {
        int result = commentMapper.deleteByParent(boardNo);
        return result;
    }

    @Override
    public List<Comments> replyList(int parentNo) throws Exception {
        List<Comments> replyList = commentMapper.replyList(parentNo);
        return replyList ;
    }

    @Override
    public int deleteReplyByParent(int parentNo) throws Exception {
        int result = commentMapper.deleteReplyByParent(parentNo);
        return result;
    }
    
}
