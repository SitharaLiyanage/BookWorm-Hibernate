package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.BookBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.BookDAO;
import lk.ijse.dto.BookDTO;
import lk.ijse.entity.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookBOImpl implements BookBO {
    BookDAO bookDAO = (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);

    public String generateNewUserID() throws IOException {
        return bookDAO.generateNewID();
    }
    @Override
    public List<BookDTO> getAllBookes() throws Exception {
        List<BookDTO> allBookes= new ArrayList<>();
        List<Book> all = bookDAO.getAll();
        for (Book book : all) {
            allBookes.add(new BookDTO(book.getId(), book.getName(), book.getAuthor(), book.getGenre(), book.getQty(), book.getBranchId()));
        }
        return allBookes;
    }

    @Override
    public boolean addBook(BookDTO dto) throws Exception {
        return bookDAO.add(new Book(dto.getId(), dto.getName(), dto.getAuthor(), dto.getGenre(), dto.getQty(), dto.getBranchId()));
    }

    @Override
    public boolean updateBook(BookDTO dto) throws Exception {
        return bookDAO.update(new Book(dto.getId(), dto.getName(), dto.getAuthor(), dto.getGenre(), dto.getQty(), dto.getBranchId()));
    }


    @Override
    public boolean deleteBook(String id) throws Exception {
        return bookDAO.delete(id);
    }

    public BookDTO search(String id) throws Exception {
        Book book = bookDAO.search(id);
        if (book != null) {
            return new BookDTO(book.getId(), book.getName(), book.getAuthor(), book.getGenre(), book.getQty(), book.getBranchId());
        } else {
            return null;
        }
    }


}
