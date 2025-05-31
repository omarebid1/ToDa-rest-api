package com.userservice.service;

import brevo.ApiClient;
import brevo.ApiException;
import brevo.Configuration;
import brevoApi.TransactionalEmailsApi;
import brevoModel.SendSmtpEmail;
import brevoModel.SendSmtpEmailSender;
import brevoModel.SendSmtpEmailTo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    private final TransactionalEmailsApi brevoApi;

    @PostConstruct
    public void init() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setApiKey(brevoApiKey);
    }

    public void sendOtpEmail(String to, String otpCode) {
        String subject = "OTP for Password Reset – TODA App";

        String body = "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "  <meta charset='UTF-8'>" +
                "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "  <style>" +
                "    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');" +
                "    body { margin: 0; padding: 0; background-color: #f1f3f4; font-family: 'Roboto', Arial, sans-serif; }" +
                "    .container { background-color: #ffffff; max-width: 600px; margin: 40px auto; padding: 30px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); }" +
                "    .header { background-color: #0073e6; color: white; padding: 20px; border-radius: 10px 10px 0 0; text-align: center; font-size: 22px; font-weight: 700; }" +
                "    .content { font-size: 16px; color: #333333; line-height: 1.6; margin-top: 20px; }" +
                "    .otp-box { background-color: #e8f0fe; color: #1a73e8; font-size: 32px; font-weight: 600; padding: 15px; text-align: center; border-radius: 8px; margin: 30px 0; letter-spacing: 2px; }" +
                "    .footer { margin-top: 40px; text-align: center; font-size: 13px; color: #888888; }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "  <div class='container'>" +
                "    <div class='header'>Password Reset OTP</div>" +
                "    <div class='content'>" +
                "      <p>Hello,</p>" +
                "      <p>We received a request to reset your password for your TODA App account.</p>" +
                "      <p>Please use the following one-time password (OTP) to proceed:</p>" +
                "      <div class='otp-box'>" + otpCode + "</div>" +
                "      <p>This OTP is valid for <strong>5 minutes</strong>.</p>" +
                "      <p>If you did not request this change, you can safely ignore this email.</p>" +
                "      <p>Best regards,<br><strong>TODA Security Team</strong></p>" +
                "    </div>" +
                "    <div class='footer'>This is an automated message. Please do not reply.</div>" +
                "  </div>" +
                "</body>" +
                "</html>";

        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(senderEmail);
        sender.setName(senderName);

        SendSmtpEmailTo recipient = new SendSmtpEmailTo();
        recipient.setEmail(to);

        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(List.of(recipient));
        sendSmtpEmail.setSubject(subject);
        sendSmtpEmail.setHtmlContent(body);

        try {
            brevoApi.sendTransacEmail(sendSmtpEmail);
            System.out.println("OTP email sent successfully to " + to);
        } catch (ApiException e) {
            System.err.println("Error sending OTP email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

