package org.gluecoders.library.models;

import net.sf.oval.Validator;
import org.gluecoders.library.config.Oval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Month;
import java.time.YearMonth;

import static org.junit.Assert.*;
/**
 * Created by Anand_Rajneesh on 6/14/2017.
 */
@RunWith(SpringRunner.class)
@Import({Oval.class})
public class BookTest {

    private Book book;

    @Autowired
    private Validator validator;

    @Before
    public void setup(){
        book = Book.builder()
                .author("Joshua Bloch")
                .categories("Programming", "Java")
                .isbn(1234567890L)
                .publisher("noidea")
                .yearOfPublishing(2011, Month.AUGUST)
                .title("Effective Java")
                .build();
    }

    private void assertValidation(Book book){
        assertFalse(validator.validate(book).isEmpty());
    }

    @Test
    public void testBuilder(){
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals(1234567890L, book.getIsbnCode());
        assertEquals("noidea", book.getPublisher());
        assertEquals("Effective Java", book.getTitle());
        assertEquals(YearMonth.of(2011, Month.AUGUST), book.getPublishedYear());
        assertTrue(book.getCategories().contains("Programming"));
        assertTrue(book.getCategories().contains("Java"));
    }

    @Test
    public void testMissingAuthor(){
        book.setAuthor(null);
        assertValidation(book);
    }

    @Test
    public void testMissingPublisher(){
        book.setPublisher(null);
        assertValidation(book);
    }

    @Test
    public void testMissingTitle(){
        book.setTitle(null);
        assertValidation(book);
    }

    @Test
    public void testMissingPublishedYear(){
        book.setPublishedYear(null);
        assertValidation(book);
    }

    @Test
    public void testMissingIsbnCode(){
        book.setIsbnCode(0);
        assertValidation(book);
    }

    @Test
    public void testMissingCategories(){
        book.setCategories(null);
        assertTrue(validator.validate(book).isEmpty());
    }
}
