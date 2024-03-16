package lk.ijse.dto.tm;

import lombok.*;
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Setter
    @Getter

    public class UserTM {
        private String id;
        private String username;
        private String email;
        private String password;
        private String repeatpassword;
    }