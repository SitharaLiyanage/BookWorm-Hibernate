package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String password;
    private String repeatpassword;
}
