package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-26T16:04:26+0600",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorModel authorDtoToAuthorModel(AuthorDtoRequest arg0) {
        if ( arg0 == null ) {
            return null;
        }

        AuthorModel authorModel = new AuthorModel();

        authorModel.setId( arg0.getId() );
        authorModel.setName( arg0.getName() );

        return authorModel;
    }

    @Override
    public AuthorDtoResponse authorModelToAuthorDto(AuthorModel arg0) {
        if ( arg0 == null ) {
            return null;
        }

        AuthorDtoResponse authorDtoResponse = new AuthorDtoResponse();

        authorDtoResponse.setNewsDtoResponseList( newsModelListToNewsDtoResponseList( arg0.getNewsModelList() ) );
        authorDtoResponse.setId( arg0.getId() );
        authorDtoResponse.setName( arg0.getName() );
        authorDtoResponse.setCreateDate( arg0.getCreateDate() );
        authorDtoResponse.setLastUpdateDate( arg0.getLastUpdateDate() );

        return authorDtoResponse;
    }

    @Override
    public List<AuthorDtoResponse> authorModelListToAuthorDtoList(List<AuthorModel> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<AuthorDtoResponse> list = new ArrayList<AuthorDtoResponse>( arg0.size() );
        for ( AuthorModel authorModel : arg0 ) {
            list.add( authorModelToAuthorDto( authorModel ) );
        }

        return list;
    }

    protected NewsDtoResponse newsModelToNewsDtoResponse(NewsModel newsModel) {
        if ( newsModel == null ) {
            return null;
        }

        NewsDtoResponse newsDtoResponse = new NewsDtoResponse();

        newsDtoResponse.setId( newsModel.getId() );
        newsDtoResponse.setTitle( newsModel.getTitle() );
        newsDtoResponse.setContent( newsModel.getContent() );
        newsDtoResponse.setCreateDate( newsModel.getCreateDate() );
        newsDtoResponse.setLastUpdateDate( newsModel.getLastUpdateDate() );

        return newsDtoResponse;
    }

    protected List<NewsDtoResponse> newsModelListToNewsDtoResponseList(List<NewsModel> list) {
        if ( list == null ) {
            return null;
        }

        List<NewsDtoResponse> list1 = new ArrayList<NewsDtoResponse>( list.size() );
        for ( NewsModel newsModel : list ) {
            list1.add( newsModelToNewsDtoResponse( newsModel ) );
        }

        return list1;
    }
}
