package com.areatak.gazette.entities;

import com.areatak.util.FileTypeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by alirezaghias on 4/10/2017 AD.
 */
@Table(name = "t_media")
@Entity
public class Media extends BaseEntity {
    @Column(name = "c_fileName")
    private String fileName;
    @Column(name = "c_address",unique = true)
    private String address;
    @Column(name = "c_type")
    private FileTypeEnum fileType;
    @ManyToOne
    private User user;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileTypeEnum getFileType() {
        return fileType;
    }

    public void setFileType(FileTypeEnum fileType) {
        this.fileType = fileType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
