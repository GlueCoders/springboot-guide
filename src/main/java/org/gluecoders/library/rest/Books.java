package org.gluecoders.library.rest;

import org.gluecoders.library.models.Book;
import org.gluecoders.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/10/2017.
 */
@RestController()
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/books")
public class Books {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> list = bookService.getAllBooks();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable("isbn") long isbnCode) {
        Book book = bookService.getBookByISBN(isbnCode);
        if(book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        book = bookService.addBook(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") long isbnCode) {
        bookService.deleteBook(isbnCode);
        return new ResponseEntity(HttpStatus.OK);
    }

}

