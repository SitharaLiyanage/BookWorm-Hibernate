package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class TransactionDTO {
    private String tranID;
    private String memID;
    private String memName;
    private String bookID;
    private String bookName;
    private String tranDate;
    private String tranEndDate;
}
