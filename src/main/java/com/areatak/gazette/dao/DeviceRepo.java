package com.areatak.gazette.dao;

import com.areatak.gazette.entities.Device;
import com.areatak.gazette.entities.User;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by alirezaghias on 3/12/2017 AD.
 */
@Repository
public interface DeviceRepo extends CrudRepository<Device, String>, QueryDslPredicateExecutor<Device> {
    Device findByToken(String token);
    List<Device> findByUser(User user);
}
