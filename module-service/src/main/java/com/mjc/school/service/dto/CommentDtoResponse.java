package com.mjc.school.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDtoResponse {
    private Long id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    @JsonIgnoreProperties(value = {"authorDtoResponse", "tagDtoResponseSet", "commentDtoResponseList"})
    private NewsDtoResponse newsDtoResponse;

    @Override
    public String toString() {
        return "CommentDtoResponse{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", newsId=" + newsDtoResponse.getId() +
                '}';
    }
}
