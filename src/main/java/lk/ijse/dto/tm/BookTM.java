package lk.ijse.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class BookTM {
    private String id;
    private String name;
    private String author;
    private String genre;
    private int qty;
    private String branchId;
}
