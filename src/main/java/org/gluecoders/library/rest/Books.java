package org.gluecoders.library.rest;

import org.gluecoders.library.models.Book;
import org.gluecoders.library.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/10/2017.
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/books")
public class Books {

    private final static Logger LOGGER = LoggerFactory.getLogger(Books.class);

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        LOGGER.info("getAllBooks invoked");
        List<Book> list = bookService.getAllBooks();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable("isbn") long isbnCode) {
        LOGGER.info("getBook invoked for ISBN code {}", isbnCode);
        Book book = bookService.getBookByISBN(isbnCode);
        if(book == null) {
            LOGGER.debug("Book not found for ISBN code {}", isbnCode);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            LOGGER.debug("Book found for ISBN code {} {}", isbnCode, book);
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        LOGGER.info("addBook invoked for Book {}", book);
        book = bookService.addBook(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") long isbnCode) {
        LOGGER.info("deleteBook invoked for ISBN Code {}", isbnCode);
        bookService.deleteBook(isbnCode);
        return new ResponseEntity(HttpStatus.OK);
    }

}

