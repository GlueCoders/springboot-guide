package org.gluecoders.library.dao;

import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import org.gluecoders.library.config.Database;
import org.gluecoders.library.testHelpers.dao.FakeMongo;
import org.gluecoders.library.models.Book;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.junit.Assert.*;

/**
 * Created by Anand_Rajneesh on 6/14/2017.
 * TODO FIXME - Text based search queries are not working in test mode, Fongo not able to set up text index ?
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
        Book book = bookDao.findDistinctByIsbnCode("123456789000");
        assertNotNull("Book should be returned for 1234567890isbn ", book);
    }


    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/books.json")
    public void findDistinctByIsbnCode_IsbnNotAvailable(){
        Book book = bookDao.findDistinctByIsbnCode("123456789200");
        assertNull("No book should be returned for 1234567892 isbn ", book);
    }


    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.DELETE_ALL)
    public void findDistinctByIsbnCode_NoDataAvailable(){
        Book book = bookDao.findDistinctByIsbnCode("123456789000");
        assertNull("No Book should be returned since there is no data present ", book);
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/empty.json")
    @ShouldMatchDataSet(location = "/books/book1.json")
    public void addBook(){
        Book book = Book.builder()
                .author("Joshua Bloch")
                .categories("Programming", "Java")
                .isbn("123456789000")
                .publisher("noidea")
                .yearOfPublishing(2011, Month.AUGUST)
                .title("Effective Java")
                .build();
        bookDao.save(book);
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/books.json")
    public void deleteBook_WithId(){
        Book book = bookDao.findDistinctByIsbnCode("123456789000");
        bookDao.delete(book);
    }

    @Test(expected = IllegalArgumentException.class)
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/books.json")
    public void deleteBook_WithoutId(){
        Book book = bookDao.findDistinctByIsbnCode("123456789000");
        book.setId(null);
        bookDao.delete(book);
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations = "/books/books.json")
    public void deleteBook_IdNotPresent(){
        Book book = bookDao.findDistinctByIsbnCode("123456789000");
        book.setId("12323543453412");
        bookDao.delete(book);
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/empty.json")
    public void findBooks_NoBooksAvailable(){
        List<Book> books = FindTester.test(bookDao).noArgs().get();
        assertTrue(books.isEmpty());
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableMatchingCategory1(){
        List<Book> books = FindTester.test(bookDao).category("Technology").get();
        assertTrue(books.size() == 1);
        assertTrue(books.get(0).getIsbnCode().equals("978047101994700"));
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableMatchingCategory2(){
        List<Book> books = FindTester.test(bookDao).category("Science", "Java").get();
        assertTrue(books.size() == 6);
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableNoneMatchingCategory(){
        List<Book> books = FindTester.test(bookDao).category("NeverMatching").get();
        assertTrue(books.size() == 0);
    }

    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableMatchingAuthor(){
        List<Book> books = FindTester.test(bookDao).author("Moss").get();
        assertTrue(books.size() == 1);
        assertTrue(books.get(0).getIsbnCode().equals("978041210490900"));
    }

    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableNotMatchingAuthor(){
        List<Book> books = FindTester.test(bookDao).author("MossXYZ").get();
        assertTrue(books.size() == 0);
    }

    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableMatchingTitle(){
        List<Book> books = FindTester.test(bookDao).title("Advanced").get();
        assertTrue(books.size() == 4);
    }

    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableNotMatchingTitle(){
        List<Book> books = FindTester.test(bookDao).title("AdvancedSuper").get();
        assertTrue(books.size() == 0);
    }

    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableMatchingYear(){
        List<Book> books = FindTester.test(bookDao).publishedYear("2001").get();
        assertTrue(books.size() == 2);
    }

    @Test(expected = NumberFormatException.class)
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableMatchingYear_UnhandledError(){
        List<Book> books = FindTester.test(bookDao).publishedYear("2001s").get();
        assertTrue(books.size() == 0);
    }

    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableCustom1(){
        List<Book> books = FindTester.test(bookDao).title("Java").publishedYear("2001").get();
        assertTrue(books.size() == 2);
    }

    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableCustom2(){
        List<Book> books = FindTester.test(bookDao).category("Science").title("Java").publishedYear("2001").get();
        assertTrue(books.size() == 0);
    }

    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableCustom3(){
        List<Book> books = FindTester.test(bookDao).category("Science", "Java").title("Java").publishedYear("2001").get();
        assertTrue(books.size() == 2);
    }

    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableCustom4(){
        List<Book> books = FindTester.test(bookDao).title("Advanced").publishedYear("1996").get();
        assertTrue(books.size() == 2);
    }

    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableCustom5(){
        List<Book> books = FindTester.test(bookDao).title("Advanced").publishedYear("1999").get();
        assertTrue(books.size() == 1);
    }

    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableCustom6(){
        List<Book> books = FindTester.test(bookDao).author("William Harvey").get();
        assertTrue(books.size() == 2);
    }


    @Test @Ignore
    @UsingDataSet(loadStrategy = LoadStrategyEnum.CLEAN_INSERT, locations="/books/manybooks.json")
    public void findBooks_BooksAvailableCustom7(){
        List<Book> books = FindTester.test(bookDao).author("William Harvey").category("Java").get();
        assertTrue(books.size() == 1);
    }

    private static class FindTester{

        private BookDao testObject;
        private List<String> categories = null;
        private String author = null;
        private String title = null;
        private String publishedYear = null;
        private List<Book> books = null;
        public static FindTester test(BookDao bookDao){
            FindTester t = new FindTester();
            t.testObject = bookDao;
            return t;
        }

        public Supplier<List<Book>> noArgs(){
            return () -> testObject.findBooks(null,null,null,null);
        }

        public FindTester category(String ... category){
            this.categories = Optional.ofNullable(this.categories).orElseGet(ArrayList::new);
            this.categories.addAll(Arrays.asList(category));
            return this;
        }

        public FindTester author(String author){
            this.author = author;
            return this;
        }

        public FindTester title(String title){
            this.title = title;
            return this;
        }

        public FindTester publishedYear(String publishedYear){
            this.publishedYear = publishedYear;
            return this;
        }

        public List<Book> get(){
            return testObject.findBooks(categories, author, title, publishedYear);
        }

    }

}
