package com.areatak.util;

/**
 * Created by asus on 4/23/2017.
 */
public enum FileTypeEnum {
    PROFILE_PICTURE_FILE("PROFILE_PIC"),
    UPLOADED_FILE("UPLOADED_FILE");

    private String value;

    private FileTypeEnum(String value){
        this.value = value;
    }
}
