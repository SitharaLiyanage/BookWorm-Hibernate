package lk.ijse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
public class Book {
    @Id
    private String id;
    private String name;
    private String author;
    private String genre;
    private int qty;
    private String branchId;
}
