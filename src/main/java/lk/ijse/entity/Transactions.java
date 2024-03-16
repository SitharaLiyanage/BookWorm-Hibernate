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
public class Transactions {
    @Id
    private String tranID;
    private String memID;
    private String memName;
    private String bookID;
    private String bookName;
    private String tranDate;
    private String tranEndDate;
}
