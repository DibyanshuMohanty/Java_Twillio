package com.yourcompany.otpprovider;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class TwilioOTPSender {
    private static final String PROPERTIES_FILE = "/application.properties";
    private static final String ACCOUNT_SID_PROPERTY = "twilio.account.sid";
    private static final String AUTH_TOKEN_PROPERTY = "twilio.auth.token";
    private static final String TWILIO_PHONE_NUMBER_PROPERTY = "twilio.phone.number";
    private static final String RECIPIENT_PHONE_NUMBER_PROPERTY = "recipient.phone.number";

    public static void main(String[] args) throws IOException {
        Properties properties = loadProperties();
        String accountSid = properties.getProperty(ACCOUNT_SID_PROPERTY);
        String authToken = properties.getProperty(AUTH_TOKEN_PROPERTY);
        String twilioPhoneNumber = properties.getProperty(TWILIO_PHONE_NUMBER_PROPERTY);
        String recipientPhoneNumber = properties.getProperty(RECIPIENT_PHONE_NUMBER_PROPERTY);
        Twilio.init(accountSid, authToken);
        String otp = OTPGenerator.generateRandomOTP();
        Message message = Message.creator(
                new PhoneNumber(recipientPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                "Your OTP is: " + otp)
                .create();
        System.out.println("OTP sent with SID: " + message.getSid());
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = TwilioOTPSender.class.getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(input);
        }
        return properties;
    }
}
