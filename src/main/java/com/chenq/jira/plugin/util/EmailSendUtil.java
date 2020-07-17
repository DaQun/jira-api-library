package com.chenq.jira.plugin.util;

import com.atlassian.jira.mail.Email;
import com.atlassian.mail.MailException;
import com.atlassian.mail.MailFactory;
import com.atlassian.mail.server.SMTPMailServer;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;
import java.util.List;
import java.util.Objects;

/**
 * 邮件发送类
 * Created by chenq
 * 2020/2/11 16:42
 */
@Named
@Slf4j
public class EmailSendUtil {

    private final SMTPMailServer mailServer = MailFactory.getServerManager().getDefaultSMTPMailServer();

    private void sendMail(String to, String subject, String body) {
        Email email = new Email(to);
        email.setSubject(subject);//邮件主题
        email.setBody(body);//邮件正文
        sendMail(email);
    }

    public void sendMails(List<String> tos, String subject, String body) {
        for (String to : tos) {
            sendMail(to, subject, body);
        }
    }

    private void sendMail(Email email) {
        if (Objects.isNull(mailServer)) {
            log.warn("SMTP Mail Server is not found. Please configure new SMTP mail server.");
            return;
        }
        try {
            mailServer.send(email);
        } catch (MailException e) {
            log.error(e.getMessage(), e);
        }
    }
}
