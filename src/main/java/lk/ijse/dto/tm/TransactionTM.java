package lk.ijse.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class TransactionTM {
    private String tranID;
    private String memID;
    private String memName;
    private String bookID;
    private String bookName;
    private String tranDate;
    private String tranEndDate;
}

