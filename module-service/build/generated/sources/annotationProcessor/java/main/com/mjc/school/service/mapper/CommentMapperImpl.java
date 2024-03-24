package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-23T18:48:01+0600",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentModel commentDtoToCommentModel(CommentDtoRequest commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        CommentModel commentModel = new CommentModel();

        commentModel.setNewsModel( newsIdToNewsModel( commentDto.getNewsId() ) );
        commentModel.setId( commentDto.getId() );
        commentModel.setContent( commentDto.getContent() );

        return commentModel;
    }

    @Override
    public CommentDtoResponse commentModelToCommentDto(CommentModel commentModel) {
        if ( commentModel == null ) {
            return null;
        }

        CommentDtoResponse commentDtoResponse = new CommentDtoResponse();

        commentDtoResponse.setId( commentModel.getId() );
        commentDtoResponse.setContent( commentModel.getContent() );
        commentDtoResponse.setCreateDate( commentModel.getCreateDate() );
        commentDtoResponse.setLastUpdateDate( commentModel.getLastUpdateDate() );

        return commentDtoResponse;
    }

    @Override
    public List<CommentDtoResponse> commentModelListToCommentDtoList(List<CommentModel> commentModelList) {
        if ( commentModelList == null ) {
            return null;
        }

        List<CommentDtoResponse> list = new ArrayList<CommentDtoResponse>( commentModelList.size() );
        for ( CommentModel commentModel : commentModelList ) {
            list.add( commentModelToCommentDto( commentModel ) );
        }

        return list;
    }

    @Override
    public List<CommentModel> commentIdListToCommentModelList(List<Long> commentIdList) {
        if ( commentIdList == null ) {
            return null;
        }

        List<CommentModel> list = new ArrayList<CommentModel>( commentIdList.size() );
        for ( Long long1 : commentIdList ) {
            list.add( commentIdToCommentModel( long1 ) );
        }

        return list;
    }

    @Override
    public CommentModel commentIdToCommentModel(Long commentId) {
        if ( commentId == null ) {
            return null;
        }

        CommentModel commentModel = new CommentModel();

        commentModel.setId( commentId );

        return commentModel;
    }

    @Override
    public NewsModel newsIdToNewsModel(Long newsId) {
        if ( newsId == null ) {
            return null;
        }

        NewsModel newsModel = new NewsModel();

        newsModel.setId( newsId );

        return newsModel;
    }
}
