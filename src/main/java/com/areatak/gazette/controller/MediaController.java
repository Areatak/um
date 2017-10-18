package com.areatak.gazette.controller;

import com.areatak.gazette.dao.MediaRepo;
import com.areatak.gazette.entities.Media;
import com.areatak.gazette.entities.User;
import com.areatak.gazette.exception.MediaException;
import com.areatak.util.Logger;
import com.areatak.util.FileTypeEnum;
import com.areatak.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * Created by alirezaghias on 4/3/2017 AD.
 */
@Controller
public class MediaController {
    @Value("${fileStorage.rootPath}")
    private String rootPath;
    @Value("${fileStorage.profileDirName}")
    private String profileDirName;
    @Value("${fileStorage.uploadedDirName}")
    private String uploadedDirName;

    @Autowired
    private MediaRepo mediaRepo;


    /**
     * @param user     which user uploaded this file
     * @param fileName original name of the file
     * @param fileType type of the file
     * @return
     */
    public Media saveFile(User user, FileTypeEnum fileType, String fileName, byte[] fileContent) throws MediaException {
        if (StringUtils.isEmpty(fileName)) {
            Logger.info("MediaController", "saveFile", fileName, "file name is empty");
            throw new MediaException();
        }
        if (user == null) {
            Logger.info("MediaController", "saveFile", "user", "empty user");
            throw new MediaException();
        }
        String fullAddress;
        try {
            fullAddress = saveFile(user.getId(), fileType, fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //
        Media media = new Media();
        media.setFileName(fileName);
        media.setFileType(fileType);
        media.setAddress(fullAddress);
        media.setUser(user);

        media = mediaRepo.save(media);

        return media;
    }

    /**
     * @param user
     * @param multipartFile
     * @return
     */
    public Media saveFile(User user, FileTypeEnum fileType, MultipartFile multipartFile) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                Media media = saveFile(user, fileType, StringUtil.convertToUTF8(multipartFile.getOriginalFilename()), multipartFile.getBytes());
                return media;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MediaException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param user
     * @return the Media Id of the profile pic
     */
    public String getUserProfileId(User user) {
        if (user == null) {
            Logger.info("MediaController", "getUserProfileId", "user", "empty user");
            return "";
        }
        List<Media> profileMedia = mediaRepo.findByUserAndFileType(user, FileTypeEnum.PROFILE_PICTURE_FILE, new Sort(Sort.Direction.DESC, "created"));
        if (profileMedia != null && profileMedia.size() > 0)
            return profileMedia.get(0).getId();
        return "";
    }

    /**
     * @param user
     * @return the Media of the profile pic
     */
    public File getUserProfileFile(User user) {
        if (user == null) {
            Logger.info("MediaController", "getUserProfileFile", "user", "empty user");
            return null;
        }

        List<Media> profileMedia = mediaRepo.findByUserAndFileType(user, FileTypeEnum.PROFILE_PICTURE_FILE, new Sort(Sort.Direction.DESC, "created"));
        if (profileMedia == null || profileMedia.size() <= 0) {
            Logger.info("MediaController", "getUserProfileFile", "user", "no profile media found for user=" + user.getId());
            return null;
        }
        try {
            File file = getFile(profileMedia.get(0).getAddress());
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            Logger.info("MediaController", "getUserProfileFile", "address",
                    "getting file exception for address =" + profileMedia.get(0).getAddress() + ", userId=" + user.getId());
            return null;
        }
    }

    /**
     * return the specific media
     *
     * @param mediaId
     * @return
     */
    public File getFileByMediaId(String mediaId) {
        Media media = mediaRepo.findOne(mediaId);
        if (media == null) {

        }
        try {
            File file = getFile(media.getAddress());
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            Logger.info("MediaController", "getFileByMediaId", "mediaId", "getting file exception for mediaId =" + mediaId);
            return null;
        }

    }

    //region private functions

    /**
     * @param userId   userId to keep his files in a separated directory
     * @param fileType type of file to indicate which directory should placed.
     * @param content  content of the file
     * @return complete path of the randomly name file.
     * @throws IOException
     */
    private String saveFile(String userId, FileTypeEnum fileType, byte[] content) throws IOException {
        if (content == null || content.length <= 0)
            throw new IOException("File content is empty.");

        //file name generated randomly in the server and real file name will saved in Media entity in db
        String fileName = UUID.randomUUID().toString();

        File dir = getOrCreateDir(fileType, userId);

        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(serverFile));

        outputStream.write(content);
        outputStream.close();

        return serverFile.getAbsolutePath();
    }

    /**
     * find and get a file saved in server before
     *
     * @param completeFilePath absolute path of the file in server
     * @return found file
     * @throws IOException
     */
    private File getFile(String completeFilePath) throws IOException {
        File file = new File(completeFilePath);
        if (!file.exists())
            throw new FileNotFoundException("file not found.");

        //byte[] content = IOUtils.toByteArray(new FileInputStream(completeFilePath));

        return file;
    }

    private File getOrCreateDir(FileTypeEnum fileType, String userId) {
        String dirAddress = rootPath + File.separator;
        if (fileType == FileTypeEnum.PROFILE_PICTURE_FILE)
            dirAddress += profileDirName;
        else
            dirAddress += uploadedDirName;
        if (!StringUtils.isEmpty(userId)) {
            dirAddress += File.separator + userId;
        }
        File dir = new File(dirAddress);
        if (!dir.exists())
            dir.mkdirs();

        return dir;
    }
    //endregion
}
