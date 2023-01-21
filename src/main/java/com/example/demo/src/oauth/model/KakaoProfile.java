package com.example.demo.src.oauth.model;

import lombok.Data;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.Properties;

@Data
public class KakaoProfile {
    public Long id;
    public String connected_at;
    public Properties properties;
    public KaKaoAccount kakao_account;

    @Data
    public class Properties {
        public String nickname;
    }

    @Data
    public class KaKaoAccount {
        public Boolean profile_nickname_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
        public Boolean has_age_range;
        public Boolean age_range_needs_agreement;
        public String age_range;
        public Boolean has_birthday;
        public Boolean birthday_needs_agreement;
        public String birthday;
        public String birthday_type;

        @Data
        public class Profile {
            public String nickname;
        }

    }

}
