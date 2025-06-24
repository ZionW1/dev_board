package com.aloha.community.domain;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Files {

    private int no;
    private String id;
    private String fileName;    // 파일명
    private String filePath;    // 파일경로
    private Long fileSize;    // 용량
    private String type;    // 타입 ('main', 'sub')
    private String parentTable;    // 부모테이블 ex) 'board'
    private int parentNo;    // 부모번호 10
    private String createdAt;
    private String updatedAt;

    private MultipartFile file; // 파일 데이터

    public Files() {
        this.id=UUID.randomUUID().toString();
    }
}
