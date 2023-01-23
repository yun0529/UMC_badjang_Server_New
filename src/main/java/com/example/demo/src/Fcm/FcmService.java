/*
package com.example.demo.src.Fcm;

import com.example.demo.config.BaseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static com.example.demo.config.BaseResponseStatus.*;
@Service
@Component
@RequiredArgsConstructor
public class FcmService {
    private final FcmDao fcmDao;
    private final ObjectMapper objectMapper;

    //클래스의 인스턴스를 만들지 않고 '정적'클래스를 사용하려고하는 초보자에게 매우 흔한 오류

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/badjang-ea2ea/messages:send";

    //매개변수로 전달받은 targetToken에 해당하는 device로 fcm푸시알림을 전소요청
    //target토는의 경우 fcm을 이요해 front를 구현할때 얻을 수 있다.
    public int checkUserPhone(String user_phone) throws BaseException {
        try{
            return fcmDao.checkUserPhone(user_phone);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //존재하는 회원인지 의미적 VALIDATION
    public void sendMessageTo(String user_phone, String targetToken) throws IOException, BaseException {
        if(checkUserPhone(user_phone) == 0) {
            throw new BaseException(NON_EXISTENT_PHONENUMBER);
        }

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        String title = "받장 휴대폰인증 테스트 메시지";
        String body = "인증번호는" + "[" + numStr + "]" + "입니다.";

        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken()) //header에 accesstoken을 추가
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        ).build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage); //FcmMessage 생성후 objectmapper를 통해 string으로 변환하여 반환
    }

    //json파일의 사용자 인증정보를 사용하여 액세스 토큰(fcm을 이용할수 있는 권한이 부여된)을 발급받는다. 발급받은 accesstoken은 header에 포함하여, push 알림을 요청한다.
    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "src/main/resources/badjang-adminsdk.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
                //List.of
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue(); //토큰값을 최종적으로 얻어옵니다
    }
}
*/
