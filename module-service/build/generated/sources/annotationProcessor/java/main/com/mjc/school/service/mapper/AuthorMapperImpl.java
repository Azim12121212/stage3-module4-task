package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-23T18:48:00+0600",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorModel authorDtoToAuthorModel(AuthorDtoRequest authorDto) {
        if ( authorDto == null ) {
            return null;
        }

        AuthorModel authorModel = new AuthorModel();

        authorModel.setId( authorDto.getId() );
        authorModel.setName( authorDto.getName() );

        return authorModel;
    }

    @Override
    public AuthorDtoResponse authorModelToAuthorDto(AuthorModel authorModel) {
        if ( authorModel == null ) {
            return null;
        }

        AuthorDtoResponse authorDtoResponse = new AuthorDtoResponse();

        authorDtoResponse.setId( authorModel.getId() );
        authorDtoResponse.setName( authorModel.getName() );
        authorDtoResponse.setCreateDate( authorModel.getCreateDate() );
        authorDtoResponse.setLastUpdateDate( authorModel.getLastUpdateDate() );

        return authorDtoResponse;
    }

    @Override
    public List<AuthorDtoResponse> authorModelListToAuthorDtoList(List<AuthorModel> authorModelList) {
        if ( authorModelList == null ) {
            return null;
        }

        List<AuthorDtoResponse> list = new ArrayList<AuthorDtoResponse>( authorModelList.size() );
        for ( AuthorModel authorModel : authorModelList ) {
            list.add( authorModelToAuthorDto( authorModel ) );
        }

        return list;
    }
}
