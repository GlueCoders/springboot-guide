package org.gluecoders.library.dao;

import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import org.gluecoders.library.config.Database;
import org.gluecoders.library.dao.config.FakeMongo;
import org.gluecoders.library.models.Book;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Month;
import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.junit.Assert.*;

/**
 * Created by Anand_Rajneesh on 6/14/2017.
 */
@RunWith(SpringRunner.class)
@Import(value = {FakeMongo.class, Database.class})
@EnableMongoRepositories(basePackageClasses = {BookDao.class})
public class BookDaoTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BookDao bookDao;

    @Rule
    public MongoDbRule embeddedMongoDbRule = newMongoDbRule().defaultSpringMongoDb("mockDB");

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.DELETE_ALL)
    public void getAllBooks_NoBooks() {
        List<Book> books = bookDao.findAll();
        assertTrue("Returned book list should be empty", books.isEmpty());
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/books.json")
    public void getAllBooks_WithBooks() {
        List<Book> books = bookDao.findAll();
        assertFalse("Returned book list should not be empty", books.isEmpty());
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/books.json")
    public void findDistinctByIsbnCode_IsbnAvailable(){
        Book book = bookDao.findDistinctByIsbnCode(1234567890L);
        assertNotNull("Book should be returned for 1234567890isbn ", book);
    }


    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/books.json")
    public void findDistinctByIsbnCode_IsbnNotAvailable(){
        Book book = bookDao.findDistinctByIsbnCode(1234567892L);
        assertNull("No book should be returned for 1234567892 isbn ", book);
    }


    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.DELETE_ALL)
    public void findDistinctByIsbnCode_NoDataAvailable(){
        Book book = bookDao.findDistinctByIsbnCode(1234567890L);
        assertNull("No Book should be returned since there is no data present ", book);
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/empty.json")
    @ShouldMatchDataSet(location = "/books/book1.json")
    public void addBook(){
        Book book = Book.builder()
                .author("Joshua Bloch")
                .categories("Programming", "Java")
                .isbn(1234567890L)
                .publisher("noidea")
                .yearOfPublishing(2011, Month.AUGUST)
                .title("Effective Java")
                .build();
        bookDao.save(book);
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/books.json")
    public void deleteBook_WithId(){
        Book book = bookDao.findDistinctByIsbnCode(1234567890L);
        bookDao.delete(book);
    }

    @Test(expected = IllegalArgumentException.class)
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/books.json")
    public void deleteBook_WithoutId(){
        Book book = bookDao.findDistinctByIsbnCode(1234567890L);
        book.setId(null);
        bookDao.delete(book);
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/books.json")
    public void deleteBook_IdNotPresent(){
        Book book = bookDao.findDistinctByIsbnCode(1234567890L);
        book.setId("12323543453412");
        bookDao.delete(book);
    }

}
