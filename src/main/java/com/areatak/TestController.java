package com.areatak;

import com.areatak.gazette.dao.UserRepo;
import com.areatak.util.MailUtil;
import com.areatak.util.QRUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;

/**
 * Created by alirezaghias on 3/14/2017 AD.
 */
@Controller
public class TestController {
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private QRUtil qrUtil;

    @RequestMapping("/testMail")
    public void testMail() {
        mailUtil.sendMail("alirezaght@gmail.com", "test", "<img  src=\"cid:QRCode\"/>", "QRCode", qrUtil.generate("test"));
    }
}
