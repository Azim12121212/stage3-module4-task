package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.mapstruct.Mapping;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "newsModel", source = "newsId")
    CommentModel commentDtoToCommentModel(CommentDtoRequest commentDto);

    CommentDtoResponse commentModelToCommentDto(CommentModel commentModel);

    List<CommentDtoResponse> commentModelListToCommentDtoList(List<CommentModel> commentModelList);

    List<CommentModel> commentIdListToCommentModelList(List<Long> commentIdList);

    @Mapping(target = "id", source = "commentId")
    CommentModel commentIdToCommentModel(Long commentId);

    @Mapping(target = "id", source = "newsId")
    public abstract NewsModel newsIdToNewsModel(Long newsId);
}