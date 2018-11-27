package com.xhh.javaApi;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

/**
 * 此类演示 Java发送邮件
 */
public class JavaMailDemo {


    public static void main(String[] args) {
        /*try {
            Properties properties = new Properties();
           //使用的协议(JavaMail规范要求)
            properties.setProperty("smtp.transport.protocol", "smtp");
            //发件人邮箱的SMTP服务器地址
            properties.setProperty("mail.smtp.host", "smtp.qq.com");
            //请求认证，参数名称与具体事项有关
            properties.setProperty("mail.smtp.auth", "true");
            //创建Session对象
            Session session = Session.getDefaultInstance(properties);
            session.setDebug(true);

            Transport ts = session.getTransport("smtp");
            ts.connect("smtp.qq.com", "1203502972@qq.com", "zefiprafzxzngfaf");

            //发送简单邮件
            //Message msg = createTextMail(session);

            //发送带图片的邮件
            //Message msg = createImageMail(session);

            //发送带附件的邮件
            Message msg = createAttachmentMessage(session);

            ts.sendMessage(msg, msg.getAllRecipients());
            ts.close();
            System.out.println("邮件发送成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
        try {
            receiveMail();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建一封简单的邮件
     * @param session
     * @return
     */
    private static Message createTextMail(Session session){
        //创建MimeMessages实例对象
        MimeMessage msg = new MimeMessage(session);

        try {
            //设置发件人
            msg.setFrom(new InternetAddress("1203502972@qq.com"));
            //设置收件人
            //msg.setRecipient(Message.RecipientType.TO, new InternetAddress("1203502972@qq.com"));
            //设置抄送
            msg.setRecipient(Message.RecipientType.CC, new InternetAddress("raoxianlong@xinhehui.com"));
            //设置发送日期
            msg.setSentDate(new Date());
            //设置邮件主题
            msg.setSubject("饶先龙，Hello!");
            //如果需要发送HTML内容
            msg.setContent("<html><font color='red'>饶先龙</font></html>", "text/html;charset=gb2312");
            //保存最终邮件内容
            msg.saveChanges();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return msg ;
    }

    /**
     * 创建一封带图片的邮件
     * @param session
     * @return
     */
    private static Message createImageMail(Session session){
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("1203502972@qq.com"));
            //设置收件人
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("m17601377795@163.com"));
            //设置抄送
            msg.setRecipient(Message.RecipientType.CC, new InternetAddress("raoxianlong@xinhehui.com"));
            msg.setSentDate(new Date());
            msg.setSubject("JavaMail测试发送图片");

            MimeBodyPart text = new MimeBodyPart();
            text.setContent("看美女<br/><img src='cid:aaa.jpg'>","text/html;charset=UTF-8");
             //图片
            MimeBodyPart image = new MimeBodyPart();
            image.setDataHandler(new DataHandler(new FileDataSource("src\\b.jpg")));
            image.setContentID("aaa.jpg");
            //描述数据关系
            MimeMultipart mp = new MimeMultipart();
            mp.addBodyPart(text);
            mp.addBodyPart(image);
            mp.setSubType("related");

            msg.setContent(mp);
            msg.saveChanges();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 创建附件MimeBodyPart
     * @param filePath  文件地址
     * @return MimeBodyPart
     * @throws MessagingException
     */
    private static MimeBodyPart createAttachment(String filePath) throws MessagingException {
        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(filePath));
        attachment.setDataHandler(dh);
        return attachment;
    }

    /**
     * 创建带有附件和图片的复杂邮件
     * @param session
     * @return
     */
    private static Message createAttachmentMessage(Session session){
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("1203502972@qq.com"));
            //设置收件人
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("197288128@qq.com"));
            //设置抄送
            msg.setRecipient(Message.RecipientType.CC, new InternetAddress("raoxianlong@xinhehui.com"));
            //设置发送时间
            msg.setSentDate(new Date());
            //创建正文
            MimeBodyPart content = new MimeBodyPart();
            MimeMultipart contentMulti = new MimeMultipart("related");
            //创建图片
            MimeBodyPart text = new MimeBodyPart();
            text.setContent("看美女<br/><img src='cid:aaa.jpg'>","text/html;charset=UTF-8");
            //图片
            MimeBodyPart image = createAttachment("src\\b.jpg");
            image.setContentID("aaa.jpg");
            contentMulti.addBodyPart(text);
            contentMulti.addBodyPart(image);
            content.setContent(contentMulti);
            //创建附件1
            MimeBodyPart attachment1 = createAttachment("src/1.gif");
            MimeBodyPart attachment2 = createAttachment("src/3.jpg");
            //组合附件
            MimeMultipart mp = new MimeMultipart("mixed");
            mp.addBodyPart(content);
            mp.addBodyPart(attachment1);
            mp.addBodyPart(attachment2);
            msg.setContent(mp);
            msg.saveChanges();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 接受邮件
     */
    public static void receiveMail() throws MessagingException, IOException {
        //使用SSL加密
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = new Properties();
        props.setProperty("mail.pop3.host", "smtp.qq.com");
        props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.port", "995");
        props.setProperty("mail.pop3.socketFactory.port", "995");
        props.setProperty("mail.pop3.auth", "true");
        //获取连接
        Session session = Session.getDefaultInstance(props);
        //获取Store对象
        Store store = session.getStore("pop3");
        //POP3服务器认证
        store.connect("pop.qq.com", "1203502972@qq.com", "trjpctlakbazhfcg");

        //通过POP3协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
        Folder folder = store.getFolder("INBOX");
        //设置对邮件夹的权限
        folder.open(Folder.READ_WRITE);
        //得到邮件夹中的所有邮件
        Message[] messages = folder.getMessages();
        for (Message msg : messages){
            //获取主题
            String subject = msg.getSubject();
            //或者发件人地址
            Address address = msg.getFrom()[0];
            System.out.println("邮件主题: "+ subject);
            System.out.println("发件人: "+ address);
            System.out.println("邮件内容: ");
            msg.writeTo(System.out);
        }
        folder.close(false);
        store.close();
    }



}
