package com.areatak.gazette.controller;

import com.areatak.util.Logger;
import com.areatak.util.GoogleApiUtil;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.Photo;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by asus on 4/15/2017.
 */
@Controller
public class IndexController {
    @Value("${android.latest}")
    String androidLatestFileName;
    @RequestMapping(value = "/downloadApk", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadApk() throws IOException {
        Resource resource = new ClassPathResource(androidLatestFileName);
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.android.package-archive"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String testAngular(@RequestParam(name = "i", required = false) String inviteFrom, Model model) {
        //final String token = (String) params.get("token");
        if (!StringUtils.isEmpty(inviteFrom))
            model.addAttribute("inviteFrom", inviteFrom);
        return "index";
    }
    @RequestMapping(value = "/auth")
    public String auth(Model model, RedirectAttributes redirectAttributes) {
        try {
            redirectAttributes.addFlashAttribute("socialAuthMode", true);
            PeopleService service = GoogleApiUtil.getPeopleService();
            String user = "people/me";
            ListConnectionsResponse listResponse =
                    service.people().connections().list(user).setRequestMaskIncludeField("person.email_addresses,person.photos").execute();
            List<Person> persons = listResponse.getConnections();
            LinkedHashMap<String, LinkedList<String>> emailsMap = new LinkedHashMap<>();
            LinkedHashMap<String, LinkedList<String>> photosMap = new LinkedHashMap<>();

            for (Person person : persons) {
                if (person.getEmailAddresses() != null && person.getEmailAddresses().size() > 0) {
                    LinkedList<String> emails = new LinkedList<>();
                    LinkedList<String> photos = new LinkedList<>();
                    for (EmailAddress emailAddress : person.getEmailAddresses()) {
                        emails.add(emailAddress.getValue());
                    }
                    if (person.getPhotos() != null) {
                        for (Photo photo : person.getPhotos()) {
                            photos.add(photo.getUrl());
                        }
                    }
                    emailsMap.put(person.getResourceName(), emails);
                    photosMap.put(person.getResourceName(), photos);
                }
            }

            redirectAttributes.addFlashAttribute("socialAuthSuccess", true);
            redirectAttributes.addFlashAttribute("photos", new Gson().toJson(photosMap));
            redirectAttributes.addFlashAttribute("emails", new Gson().toJson(emailsMap));

        } catch (Exception ex) {
            Logger.error("UserController", "auth", "", ex.getMessage(), ex);
            redirectAttributes.addFlashAttribute("socialAuthSuccess", false);
        }
        return "redirect:/";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/recover")
    public String recoverUser(@RequestParam(name = "h") String hash, Model model) {
        String email = (String) UserController.forgotPassword.get(hash);
        if (email == null) {
            model.addAttribute("recoverMode", true);
            model.addAttribute("recoverExpired", true);
            return "index";
        } else {
            model.addAttribute("recoverMode", true);
            model.addAttribute("recoverExpired", false);
            model.addAttribute("recoverMail", email);
            model.addAttribute("recoverHash", hash);
            return "index";
        }
    }
}
