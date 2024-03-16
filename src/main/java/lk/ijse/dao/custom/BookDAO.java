package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Book;

public interface BookDAO extends CrudDAO<Book> {
    public String getTotalBooks();
}
