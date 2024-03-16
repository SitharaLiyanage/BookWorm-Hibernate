package lk.ijse.dto;


import jakarta.persistence.ManyToMany;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class BranchDTO {
    private String id;
    private String location;
    private String email;
    private String mobile;
}
