package org.gluecoders.library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gluecoders.library.models.Book;
import org.gluecoders.library.services.BookService;
import org.gluecoders.library.testHelpers.rest.MvcBootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Anand_Rajneesh on 6/11/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(Books.class)
@Import({MvcBootstrap.class})
@WithAnonymousUser
public class BooksTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper mapper;

    private final Book effectiveJavaBook = Book.builder().title("Effective Java").yearOfPublishing(2011, Month.AUGUST)
            .author("Joshua Bloch").categories("Java", "Programming").isbn(1234567890L)
            .publisher("someone").build();

    private final Book algorithmsBook = Book.builder().title("Algorithms").yearOfPublishing(2011, Month.AUGUST)
            .author("Robert Sedgewick").categories("Algorithms", "Java").isbn(1234567891L)
            .publisher("someone").build();

    private final Book prologBook = Book.builder().title("Learn Prolog Now").yearOfPublishing(2011, Month.AUGUST)
            .author("Patrick Blackburn").categories("Prolog", "Programming").isbn(1234567892L)
            .publisher("someone").build();

    private final Book scalaBook = Book.builder().title("Scala").yearOfPublishing(2011, Month.AUGUST)
            .author("Martin Odersky").categories("Scala", "Functional", "Programming").isbn(1234567893L)
            .publisher("someone").build();

    private final Book incompleteBook = Book.builder().title("Scala").yearOfPublishing(2011, Month.AUGUST)
            .author("Martin Odersky").categories("Scala", "Functional", "Programming").isbn(1234567893L)
            .build();

    private static class Behavior {
        BookService bookService;

        public static Behavior set(BookService bookService) {
            Behavior behavior = new Behavior();
            behavior.bookService = bookService;
            return behavior;
        }

        public Behavior hasNoBooks() {
            when(bookService.getAllBooks(any(), any(), any(), any())).thenReturn(Collections.emptyList());
            when(bookService.getBookByISBN(anyLong())).thenReturn(null);
            return this;
        }

        public Behavior returnSame() {
            when(bookService.addBook(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
            return this;
        }

        public Behavior returnBooks(Book... books) {
            when(bookService.getAllBooks(any(), any(), any(), any())).thenReturn(Arrays.asList(books));
            for (Book book : books) {
                when(bookService.getBookByISBN(book.getIsbnCode())).thenReturn(book);
                when(bookService.addBook(book)).thenReturn(book);
            }
            return this;
        }
    }

    @Test
    public void getAllBooks_Unauthenticated() throws Exception {
        Behavior.set(bookService).hasNoBooks();
        mvc
                .perform(get("/books"))
                .andExpect(status().isUnauthorized());
        verify(bookService, times(0)).getAllBooks(any(), any(), any(), any());
    }

    @Test
    @WithMockUser
    public void getAllBooks_NoBooks() throws Exception {
        Behavior.set(bookService).hasNoBooks();
        mvc
                .perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[]"));
        verify(bookService, times(1)).getAllBooks(any(), any(), any(), any());
    }

    @Test
    @WithMockUser
    public void getAllBooks_QueryParams() throws Exception {
        Behavior.set(bookService).hasNoBooks();
        mvc
                .perform(get("/books")
                        .param("author", "Joshua")
                        .param("title", "Java")
                        .param("category", "Technology")
                        .param("category", "Java")
                        .param("category", "Programming")
                        .param("year", "2001"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[]"));
        verify(bookService, times(1)).getAllBooks(Arrays.asList("Technology", "Java", "Programming"), "Java", "Joshua", "2001");
    }

    @Test
    @WithMockUser
    public void getAllBooks_AtLeastOneBook() throws Exception {
        Behavior.set(bookService).returnBooks(effectiveJavaBook);
        String bookContent = mapper.writeValueAsString(effectiveJavaBook);
        mvc
                .perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(String.format("[%s]", bookContent)));
        verify(bookService, times(1)).getAllBooks(any(), any(), any(), any());
    }

    @Test
    @WithMockUser
    public void getAllBooks_MoreThanOneBook() throws Exception {
        Behavior.set(bookService).returnBooks(effectiveJavaBook, scalaBook, prologBook, algorithmsBook);
        List<Book> books = Arrays.asList(effectiveJavaBook, scalaBook, prologBook, algorithmsBook);
        String booksContent = mapper.writeValueAsString(books);
        mvc
                .perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(booksContent));
        verify(bookService, times(1)).getAllBooks(any(), any(), any(), any());
    }

    @Test
    public void addBook_Positive() throws Exception {
        Behavior.set(bookService).returnSame();
        String bookContent = mapper.writeValueAsString(effectiveJavaBook);
        mvc
                .perform(post("/books")
                        .content(bookContent)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(bookContent));
        verify(bookService, times(1)).addBook(effectiveJavaBook);
    }

    @Test
    public void addBook_MissingParams() throws Exception {
        Behavior.set(bookService).returnSame();
        String bookContent = mapper.writeValueAsString(incompleteBook);
        mvc
                .perform(post("/books")
                        .content(bookContent)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(bookService, never()).addBook(incompleteBook);
    }

    @Test
    @WithMockUser
    public void getBook_Positive() throws Exception {
        Behavior.set(bookService).returnBooks(effectiveJavaBook);
        String bookContent = mapper.writeValueAsString(effectiveJavaBook);
        mvc
                .perform(get("/books/" + effectiveJavaBook.getIsbnCode()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(bookContent));
        verify(bookService, times(1)).getBookByISBN(effectiveJavaBook.getIsbnCode());
    }

    @Test
    @WithMockUser
    public void getBook_NoBookFound() throws Exception {
        Behavior.set(bookService).hasNoBooks();
        mvc
                .perform(get("/books/1234567899"))
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).getBookByISBN(anyLong());
    }

    @Test
    public void deleteBook() throws Exception {
        mvc
                .perform(delete("/books/1234567899"))
                .andExpect(status().isOk());
        verify(bookService, times(1)).deleteBook(anyLong());
    }
}
