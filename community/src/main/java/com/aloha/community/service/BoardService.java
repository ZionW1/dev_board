package com.aloha.community.service;

import java.util.List;

import com.aloha.community.domain.Board;
import com.aloha.community.domain.Option;
import com.aloha.community.domain.Page;

public interface BoardService {
    
    // 전체 목록
    public List<Board> list() throws Exception;
    // 검색 
    public List<Board> list(String keyword) throws Exception;
    // 검색 + 옵션
    public List<Board> list(Option option) throws Exception;
    // 검색 + 옵션 + 필터(개수)
    public List<Board> list(Option option, int rows) throws Exception;
    // 검색 + 옵션 + 페이징
    public List<Board> list(Option option, Page page) throws Exception;

    public Board select (String id) throws Exception;

    public int insert(Board board) throws Exception;

    public int update(Board board) throws Exception;

    public int delete(String id) throws Exception;

    // 데이터 개수
    public int count(Option option) throws Exception;

}
