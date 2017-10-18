package com.areatak.gazette.dao;

import com.areatak.gazette.entities.User;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by alirezaghias on 3/12/2017 AD.
 */
@Repository
public interface UserRepo extends CrudRepository<User, String>, QueryDslPredicateExecutor<User>, PagingAndSortingRepository<User, String> {
    User findByEmail(String email);
//    List<User> findByCreditLessThan(Double credit);
}
