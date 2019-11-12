package com.example.gameproject;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class SendMail {
    //QQ邮箱开通的stmp服务后得到的客户端授权码
    private static final String STMP_CODE = "bqsayctzegmndgbd";

    private static final String SEND_ACCOUNT = "1625303434@qq.com";

    private static final String SEND_NAME = "狗屎工作室";

    private static final String TITLE = "代码五子棋验证码";

    private static final String CONTENT = "代码五子棋，等你来战，验证码：";

    private String receiveAccount;

    private String checkCode;

    private Session session;

    public SendMail(String receiveAccount, String checkCode){
        this.receiveAccount = receiveAccount;
        this.checkCode = checkCode;
        initMail();
    }

    private void initMail(){
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        session = Session.getInstance(properties);
    }

    public boolean sendMessage(){
        // 获取邮件对象
        Message message = new MimeMessage(session);
        boolean result = true;
        try{
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress(SEND_ACCOUNT));
            // 设置收件人邮箱地址
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveAccount));
            // 设置邮件标题
            message.setSubject(TITLE);
            // 设置邮件内容
            message.setText(CONTENT + checkCode);
            //设置邮箱发送者名字
            message.setFrom(new InternetAddress(SEND_ACCOUNT, SEND_NAME));
            // 得到邮差对象
            Transport transport = session.getTransport();
            // 连接自己的邮箱账户
            transport.connect(SEND_ACCOUNT, STMP_CODE);// 密码为
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch (Exception e){
            result = false;
        }
        return result;
    }

}
