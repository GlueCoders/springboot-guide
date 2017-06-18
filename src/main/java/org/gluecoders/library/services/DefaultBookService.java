package org.gluecoders.library.services;

import org.gluecoders.library.dao.BookDao;
import org.gluecoders.library.models.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/11/2017.
 */
@Component
public class DefaultBookService implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBookService.class);

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> getAllBooks(List<String> categories, String title, String author, String year) {
        LOGGER.info("getAllBooks() invoked with search criteria {} , {}, {}, {}", categories, title, author, year);
        return bookDao.findBooks(categories, title, author, year);
    }

    @Override
    public Book addBook(Book book) {
        LOGGER.info("Adding book {} to library",book);
        return bookDao.save(book);
    }

    @Override
    public Book getBookByISBN(long isbn) {
        LOGGER.info("Get Book by ISBN {}", isbn);
        return bookDao.findDistinctByIsbnCode(isbn);
    }

    @Override
    public void deleteBook(long isbn) {
        LOGGER.info("Delete book by isbn {}",isbn);
        Book book = bookDao.findDistinctByIsbnCode(isbn);
        if(book != null) {
            bookDao.delete(book);
        }
    }
}
