package org.gluecoders.library.services;

import org.gluecoders.library.models.Book;

import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/11/2017.
 */
public interface BookService {

    List<Book> getAllBooks(List<String> categories, String title, String author, String year);

    Book addBook(Book book);

    Book getBookByISBN(String isbn);

    void deleteBook(String isbn);
}
