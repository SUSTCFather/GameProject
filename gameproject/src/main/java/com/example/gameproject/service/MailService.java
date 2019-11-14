package com.example.gameproject.service;

import com.example.gameproject.HttpUtil;
import com.example.gameproject.SendMail;
import com.example.gameproject.api.MailRepository;
import com.example.gameproject.bean.model.Mail;
import com.example.gameproject.bean.request.RegisterRequest;
import com.example.gameproject.bean.response.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;


/**
 * @author 伍凯铭
 * @since 2019/11/10
 * 邮箱Service
 */

@RestController
public class MailService {

    @Autowired
    private MailRepository mailRepository;

    /**
     * 发送验证码
     * @param mailAddress
     * @return
     */
    public HttpResult sendCheckCode(String mailAddress) {
        HttpResult httpResult = new HttpResult();
        String checkCode = createCheckCode();
        SendMail sendMail = new SendMail(mailAddress,checkCode);
        //send email
        boolean result = sendMail.sendMessage();
        if (result) {
            httpResult.setStatus(HttpUtil.SUCCESS);
            httpResult.setMessage("验证码已发送");
            saveCheckCode(mailAddress,checkCode);
        } else {
            httpResult.setStatus(HttpUtil.FAIL);
            httpResult.setMessage("验证码发送失败");
        }
        return httpResult;
    }

    private void saveCheckCode(String mailAddress, String checkCode) {
        List<Mail> lists = mailRepository.findByMailAddress(mailAddress);
        Mail mail;
        if (lists.isEmpty()) {
            mail = new Mail();
            mail.setMailAddress(mailAddress);
            mail.setCheckCode(checkCode);
            mailRepository.save(mail);
        }
        else {
            mail = lists.get(0);
            mail.setCheckCode(checkCode);
            mailRepository.save(mail);
        }
    }

    /**
     * 验证验证码
     * @param request
     * @return
     */
    public boolean verifyCheckCode(RegisterRequest request) {
        List<Mail> lists = mailRepository.findByMailAddress(request.getMailAddress());
        if (!lists.isEmpty()) {
            Mail m = lists.get(0);
            if (m.getCheckCode().equals(request.getCheckCode())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private String createCheckCode(){
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
