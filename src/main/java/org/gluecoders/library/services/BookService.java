package org.gluecoders.library.services;

import org.gluecoders.library.models.Book;

import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/11/2017.
 */
public interface BookService {

    List<Book> getAllBooks();

    Book addBook(Book book);

    Book getBookByISBN(long isbn);

    void deleteBook(long isbn);
}
