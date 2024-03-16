package lk.ijse.dto;

import jakarta.persistence.ManyToMany;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class BookDTO {
    private String id;
    private String name;
    private String author;
    private String genre;
    private int qty;
    private String branchId;
}
