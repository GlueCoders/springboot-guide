package org.gluecoders.library.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.time.Month;
import java.time.YearMonth;

import static org.junit.Assert.*;
/**
 * Created by Anand_Rajneesh on 6/14/2017.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class BookTest {

    @Test
    public void testBuilder(){
        Book book = Book.builder()
                .author("Joshua Bloch")
                .categories("Programming", "Java")
                .isbn(1234567890L)
                .publisher("noidea")
                .yearOfPublishing(2011, Month.AUGUST)
                .title("Effective Java")
                .build();

        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals(1234567890L, book.getIsbnCode());
        assertEquals("noidea", book.getPublisher());
        assertEquals("Effective Java", book.getTitle());
        assertEquals(YearMonth.of(2011, Month.AUGUST), book.getPublishedYear());
        assertTrue(book.getCategories().contains("Programming"));
        assertTrue(book.getCategories().contains("Java"));
    }
}
