package com.areatak.gazette.metadata;

/**
 * Created by alirezaghias on 3/12/2017 AD.
 */
public enum Platform {
    ANY("ANY"),
    ANDROID("ANDROID"),
    IOS("IOS"),
    WEB("WEB"),
    BALLYHOO("BALLYHOO");

    private final String value;

    Platform(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static Platform getPlatform(String value){
        for(Platform p : values())
            if(p.getValue().equalsIgnoreCase(value)) return p;
        return Platform.ANY;
    }

}
