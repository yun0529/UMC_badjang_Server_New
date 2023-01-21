package com.example.demo.src.Fcm;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmservice;

    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody RequestDTO requestDTO) throws IOException {
        System.out.println(requestDTO.getTargetToken());

        fcmservice.sendMessageTo(
                requestDTO.getTargetToken()
        );
        return ResponseEntity.ok().build();
    }
}
/*import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@RestController
public class FcmController {
    public String sms() {
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }
        return numStr;
    }

    @RequestMapping("/send/token")
    public String sendToToken() throws FirebaseMessagingException {

        // This registration token comes from the client FCM SDKs.
        String registrationToken = "토큰을 입력해주세요.";

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("title", "받장 휴대폰인증 테스트 메시지")
                .putData("content", "인증번호는" + "["+sms()+"]" + "입니다.")
                .setToken(registrationToken)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);

        return response;
    }
}*/

