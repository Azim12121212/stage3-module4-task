package com.mjc.school.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsDtoResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    @JsonIgnoreProperties(value = "newsDtoResponseList")
    private AuthorDtoResponse authorDtoResponse;
    @JsonIgnoreProperties(value = "newsDtoResponseSet")
    private Set<TagDtoResponse> tagDtoResponseSet;
    @JsonIgnoreProperties(value = "newsDtoResponse")
    private List<CommentDtoResponse> commentDtoResponseList;

    @Override
    public String toString() {
        return "NewsDtoResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", authorId=" + authorDtoResponse.getId() +
                '}';
    }
}