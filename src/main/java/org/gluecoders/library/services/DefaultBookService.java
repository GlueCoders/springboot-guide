package org.gluecoders.library.services;

import org.gluecoders.library.models.Book;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/11/2017.
 */
@Component
public class DefaultBookService implements BookService {


    @Override
    public List<Book> getAllBooks() {
        return Collections.emptyList();
    }

    @Override
    public Book addBook(Book book) {
        return book;
    }

    @Override
    public Book getBookByISBN(long isbn) {
        return null;
    }

    @Override
    public void deleteBook(long isbn) {

    }
}
