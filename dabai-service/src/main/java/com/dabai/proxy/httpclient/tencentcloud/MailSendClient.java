package com.dabai.proxy.httpclient.tencentcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.UUID;

/**
 * @author: jinshan.zhu
 * @date: 2022/9/7 22:25
 */
@Slf4j
@Component
public class MailSendClient implements InitializingBean {

    private static final String SMTP_HOST = "smtp.qcloudmail.com";
    private static final String SMTP_PORT = "465";

    private Properties props = new Properties();

    private Authenticator authenticator;

    private static final String CONTENT = "尊敬的用户您好:<br>" +
            " 携手大白，与您同行。%s电子保单详情请看附件。";

    private static final String SUBJECT = "大白Bao-%s电子保单";

    /**
     * 发送邮件
     *
     * @param sendTo      收件人
     * @param policyNo    保单编号
     * @param productName 产品名称
     * @param url         保单链接
     */
    public void sendMail(String sendTo, String policyNo, String productName, String url) {
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);

        UUID uuid = UUID.randomUUID();
        final String messageIdValue = "<" + uuid + ">";
        //创建邮件消息
        MimeMessage message = new MimeMessage(mailSession) {
            @Override
            protected void updateMessageID() throws MessagingException {
                //设置自定义Message-ID值
                setHeader("Message-ID", messageIdValue);
            }
        };
        try {
            // 设置发件人邮件地址和名称。填写控制台配置的发信地址,和mail.user保持一致。发信别名可以自定义，如test。
            InternetAddress from = new InternetAddress(props.getProperty("mail.user"), "大白Bao");
            message.setFrom(from);
            //可选。设置回信地址
            Address[] a = new Address[1];
            a[0] = new InternetAddress(props.getProperty("mail.user"));
            message.setReplyTo(a);
            //设置收件人邮件地址，比如yyy@yyy.com
            InternetAddress to = new InternetAddress(sendTo);
            message.setRecipient(MimeMessage.RecipientType.TO, to);
            //如果同时发给多人，才将上面两行替换为如下（因为部分收信系统的一些限制，尽量每次投递给一个人；同时我们限制单次允许发送的人数是50人）：
            /*InternetAddress[] adds = new InternetAddress[2];
            adds[0] = new InternetAddress("xxx@xxx.com");
            adds[1] = new InternetAddress("xxx@xxx.com");
            message.setRecipients(Message.RecipientType.TO, adds);*/

            // 设置邮件标题
            message.setSubject(String.format(SUBJECT, productName));
            //发送附件，总的邮件大小不超过10M，创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();
            //消息 text/plain（纯文本）text/html（HTML 文档）
            messageBodyPart.setText(String.format(CONTENT, policyNo));
            messageBodyPart.setHeader("Content-Type", "text/html;charset=utf-8");
            //创建多重消息
            Multipart multipart = new MimeMultipart();
            //设置文本消息部分
            multipart.addBodyPart(messageBodyPart);
            //附件部分
            messageBodyPart = new MimeBodyPart();
            //设置要发送附件的文件路径
            URL link;
            try {
                link = new URL(url);
                URLDataSource urlDataSource = new URLDataSource(link);
                messageBodyPart.setDataHandler(new DataHandler(urlDataSource));
                String filenameEncode = MimeUtility.encodeText(policyNo + ".pdf", "UTF-8", "base64");
                messageBodyPart.setFileName(filenameEncode);
                /*messageBodyPart.setHeader("Content-Transfer-Encoding", "base64");
                messageBodyPart.setHeader("Content-Disposition", "attachment");
                messageBodyPart.setHeader("Content-Type", "application/octet-stream;name=\"" + filenameEncode + "\"");*/
                multipart.addBodyPart(messageBodyPart);
            } catch (MalformedURLException e) {
                log.error("url: {}", url, e);
            }
            // 发送含有附件的完整消息
            message.setContent(multipart);
            // 发送附件代码，结束
            // 发送邮件
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("send mail error", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.port", SMTP_PORT);
        // 发件人的账号，填写控制台配置的发信地址,比如xxx@xxx.com
        props.put("mail.user", "baodan@dabai-tech.com");
        // 访问SMTP服务时需要提供的密码(在控制台选择发信地址进行设置)
        props.put("mail.password", "Dabai@2022@Stmp");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.enable", "true");
        //props.put("mail.smtp.starttls.enable","true");
        // 构建授权信息，用于进行SMTP进行身份验证
        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
    }
}
