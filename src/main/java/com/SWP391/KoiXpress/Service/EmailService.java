package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.NotEntity.EmailDetail;
import com.SWP391.KoiXpress.Exception.EmailNotVerifiedException;
import com.SWP391.KoiXpress.Model.response.EmailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TemplateEngine templateEngine;

    public boolean sendEmail(EmailDetail emailDetail)  {
        try{
            Context context = new Context();
            context.setVariable("name",emailDetail.getUser().getEmail());
            context.setVariable("button","Click Here to verify");
            context.setVariable("link",emailDetail.getLink());
            context.setVariable("email",emailDetail.getUser().getEmail());
            String template = templateEngine.process("EmailVerify",context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            messageHelper.setFrom("admin@gmail.com");
            messageHelper.setTo(emailDetail.getUser().getEmail());
            messageHelper.setText(template,true);
            messageHelper.setSubject(emailDetail.getSubject());
            javaMailSender.send(message);
            return true;
        }catch(MessagingException e){
            throw new EmailNotVerifiedException("Error sending  email, please check your mail again");
        }
    }


    private final String MailBox_API_KEY_URL = "http://apilayer.net/api/check?access_key=18c105525047d2c536134cc9638de4f9&smtp=1&format=1";

    public Boolean verifyEmail(String email){
        String url = MailBox_API_KEY_URL + "&email=" + email;
        EmailResponse emailResponse = restTemplate.getForObject(url, EmailResponse.class);
        if(emailResponse!=null){
            return emailResponse.isFormat_valid() && emailResponse.isMx_found();
        }
        return false;
    }
}
