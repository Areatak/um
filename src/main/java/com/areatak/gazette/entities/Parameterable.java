package com.areatak.gazette.entities;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by alirezaghias on 4/12/2017 AD.
 */
public interface Parameterable {
    public HashMap<String, Serializable> toParamsMap();
    public default HashMap<String, Serializable> toMoreDetailedParamsMap() {
        return toParamsMap();
    }

}
