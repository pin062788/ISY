package top.zbeboy.isy.service;


import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import top.zbeboy.isy.config.Workbook;
import top.zbeboy.isy.config.ISYProperties;
import top.zbeboy.isy.domain.tables.pojos.SystemMailbox;
import top.zbeboy.isy.domain.tables.pojos.Users;
import top.zbeboy.isy.service.util.UUIDUtils;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by Administrator on 2016/3/29.
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private MessageSource messageSource;

    @Resource
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private ISYProperties isyProperties;

    @Inject
    private Environment env;

    @Resource
    private SystemMailboxService systemMailboxService;

    @Async
    @Override
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        if (env.acceptsProfiles(Workbook.SPRING_PROFILE_DEVELOPMENT)) {
            log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                    isMultipart, isHtml, to, subject, content);

            // Prepare message using a Spring helper
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
                message.setTo(to);
                message.setFrom(isyProperties.getConstants().getMailForm());
                message.setSubject(subject);
                String str1 = content.replaceAll("<!\\[CDATA\\[", "");
                String messages = str1.replaceAll("]]>", "");
                message.setText(messages, isHtml);
                javaMailSender.send(mimeMessage);
                log.debug("Sent e-mail to User '{}'", to);
            } catch (Exception e) {
                log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
            }
        } else {
            if(isyProperties.getMail().isOpen()){
                sendAliDMMail(to, subject, content);
            } else {
                log.debug(" 管理员已关闭邮件发送 ");
            }
        }

        SystemMailbox systemMailbox = new SystemMailbox(UUIDUtils.getUUID(),new Timestamp(System.currentTimeMillis()),to);
        systemMailboxService.save(systemMailbox);

    }

    @Async
    @Override
    public void sendActivationEmail(Users users, String baseUrl) {
        log.debug("Sending activation e-mail to '{}'", users.getUsername());
        Locale locale = Locale.forLanguageTag(users.getLangKey());
        Context data = new Context();
        data.setLocale(locale);
        data.setVariable("user", users);
        data.setVariable("baseUrl", baseUrl);
        sendEmail(users.getUsername(), messageSource.getMessage("email.activation.title", null, locale), springTemplateEngine.process("mails/activationemail", data), false, true);
    }

    @Async
    @Override
    public void sendCreationEmail(Users users, String baseUrl) {
        log.debug("Sending creation e-mail to '{}'", users.getUsername());
        Locale locale = Locale.forLanguageTag(users.getLangKey());
        Context data = new Context();
        data.setLocale(locale);
        data.setVariable("user", users);
        data.setVariable("baseUrl", baseUrl);
        sendEmail(users.getUsername(), messageSource.getMessage("email.creation.title", null, locale), springTemplateEngine.process("mails/creationemail", data), false, true);

    }

    @Async
    @Override
    public void sendPasswordResetMail(Users users, String baseUrl) {
        log.debug("Sending password reset e-mail to '{}'", users.getUsername());
        Locale locale = Locale.forLanguageTag(users.getLangKey());
        Context data = new Context();
        data.setLocale(locale);
        data.setVariable("user", users);
        data.setVariable("validLink", baseUrl + "/user/login/password/forget/reset?key=" + users.getPasswordResetKey() + "&username=" + users.getUsername());
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        String content = springTemplateEngine.process("mails/passwordresetemail", data);
        sendEmail(users.getUsername(), subject, content, false, true);
    }

    @Async
    @Override
    public void sendValidEmailMail(Users users, String baseUrl) {
        log.debug("Sending valid e-mail to '{}'", users.getUsername());
        Locale locale = Locale.forLanguageTag(users.getLangKey());
        Context data = new Context();
        data.setLocale(locale);
        data.setVariable("user", users);
        data.setVariable("validLink", baseUrl + "/user/register/mailbox/valid?key=" + users.getMailboxVerifyCode() + "&username=" + users.getUsername());
        String subject = messageSource.getMessage("email.valid.title", null, locale);
        String content = springTemplateEngine.process("mails/validemail", data);
        sendEmail(users.getUsername(), subject, content, false, true);
    }

    @Async
    @Override
    public void sendAliDMMail(String userMail, String subject, String content) {
        try {
            // 配置发送邮件的环境属性
            final Properties props = new Properties();
            // 表示SMTP发送邮件，需要进行身份验证
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", isyProperties.getMail().getHost());
            props.put("mail.smtp.port", isyProperties.getMail().getPort());
            // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
            // props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            // props.put("mail.smtp.socketFactory.port", "465");
            // props.put("mail.smtp.port", "465");


            // 发件人的账号
            props.put("mail.user", isyProperties.getMail().getUser()); //是发信地址啊！！！
            // 访问SMTP服务时需要提供的密码
            props.put("mail.password", isyProperties.getMail().getPassword());

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress form = new InternetAddress(
                    props.getProperty("mail.user"));
            message.setFrom(form);

            // 设置收件人
            InternetAddress to = new InternetAddress(userMail);
            message.setRecipient(MimeMessage.RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject(subject);
            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");
            log.debug("Sent e-mail to User '{}'", userMail);
            // 发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", userMail, e.getMessage());
        }
    }

    @Override
    public String getBaseUrl(HttpServletRequest request) {
        if (env.acceptsProfiles(Workbook.SPRING_PROFILE_DEVELOPMENT)) {
            return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        } else {
            // 因nginx代理只能在此进行转换下
            return request.getScheme() + "://" + isyProperties.getConstants().getServerName() + request.getContextPath();
        }
    }
}