package com.mjc.school.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorDtoResponse {
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    @JsonIgnoreProperties(value = {"authorDtoResponse", "tagDtoResponseSet", "commentDtoResponseList"})
    private List<NewsDtoResponse> newsDtoResponseList;

    public AuthorDtoResponse(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorDtoResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}