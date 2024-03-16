package lk.ijse.dto.tm;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class BranchTM {
    private String id;
    private String location;
    private String email;
    private String mobile;
}
