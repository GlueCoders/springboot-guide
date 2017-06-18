package org.gluecoders.library.rest;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
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

    @Autowired
    private Validator validator;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(value = "category", required = false) List<String> categories,
                                                  @RequestParam(value = "title", required = false) String title,
                                                  @RequestParam(value = "author", required = false) String author,
                                                  @RequestParam(value = "year", required = false) String year) {
        LOGGER.info("getAllBooks invoked with search criteria {} , {}, {}, {}", categories, title, author, year);
        List<Book> list = bookService.getAllBooks(categories, title, author, year);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable("isbn") long isbnCode) {
        LOGGER.info("getBook invoked for ISBN code {}", isbnCode);
        Book book = bookService.getBookByISBN(isbnCode);
        if (book == null) {
            LOGGER.debug("Book not found for ISBN code {}", isbnCode);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            LOGGER.debug("Book found for ISBN code {} {}", isbnCode, book);
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        List<ConstraintViolation> violations = validator.validate(book);
        if (!violations.isEmpty()) {
            LOGGER.info("violations {} for request payload {}", violations, book);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

