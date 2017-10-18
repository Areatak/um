package com.areatak.gazette.dao;

import com.areatak.gazette.entities.Media;
import com.areatak.gazette.entities.User;
import com.areatak.util.FileTypeEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by alirezaghias on 3/12/2017 AD.
 */
@Repository
public interface MediaRepo extends CrudRepository<Media, String>, QueryDslPredicateExecutor<Media>, PagingAndSortingRepository<Media,String> {

    List<Media> findAllByFileType(FileTypeEnum fileTypeEnum);
    List<Media> findByUserAndFileType(User user,FileTypeEnum fileTypeEnum, Sort sort);
}
