package uz.pdp.lms.modules.user.service.notification;


import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private static final String ACCOUNT_SID = "your_account_sid";
    private static final String AUTH_TOKEN = "your_auth_token";

    /*public SmsService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendPasswordResetSms(String to, String token) {
        String resetLink = "https://your-domain.com/reset-password?token=" + token;
        String messageBody = "Click the link to reset your password: " + resetLink;

        Message.creator(
                new com.twilio.type.PhoneNumber(to),
                new com.twilio.type.PhoneNumber("your_twilio_phone_number"),
                messageBody
        ).create();
    }*/
}

