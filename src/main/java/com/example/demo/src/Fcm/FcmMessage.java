package com.example.demo.src.Fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
        //fcm에 push 알림을 보내기위해 준수해야하는 request body (클래스)
        private boolean validateOnly;
        private Message message;

        @Builder
        @AllArgsConstructor
        @Getter
        public static class Message {
            private Notification notification;
            private String token; //특정 device의 토큰
        }

        @Builder
        @AllArgsConstructor
        @Getter
        public static class Notification {
            private String title;
            private String body;
            private String image;
        }
}

