package com.areatak.gazette.entities;

import com.areatak.gazette.metadata.Platform;

import javax.persistence.*;

/**
 * Created by alirezaghias on 3/12/2017 AD.
 */
@Entity
@Table(name = "t_device")
public class Device extends BaseEntity {
    @Column(unique = true, name = "c_token")
    private String token;
    @Column(name = "c_info")
    private String info;
    @Column(name = "c_platform")
    @Enumerated(EnumType.ORDINAL)
    private Platform platform;
    @Column(name = "c_version")
    private Double version;
    @Column(name = "c_buildNumber")
    private Long buildNumber;
    @Column(name = "c_cid")
    private String cid;
    @Column(name = "c_fid")
    private String fid;
    @Column(name = "c_deviceId")
    private String deviceId;
    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public Long getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Long buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
