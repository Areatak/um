package com.areatak.gazette.security;

import com.areatak.gazette.dao.DeviceRepo;
import com.areatak.gazette.entities.Device;
import com.areatak.gazette.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by alirezaghias on 3/13/2017 AD.
 */
@Component
@Scope("singleton")
public class Authenticator {
    @Autowired
    DeviceRepo deviceRepo;
    public Device validate(String token) throws AuthException {
        Device device = deviceRepo.findByToken(token);
        if (device == null)
            throw new AuthException();
        else {
            return device;
        }
    }
}
