package org.gluecoders.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Month;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/10/2017.
 */
@Entity
public class Book implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue
    private long id;
    @NotNull @NotEmpty
    private String title;
    @NotNull @NotEmpty
    private String author;
    @NotNull
    private YearMonth publishedYear;
    @ElementCollection
    private List<String> categories;
    @NotNull @NotEmpty
    private String publisher;
    @NotNull
    @Length(min = 13, max = 13)
    private String isbnCode;


    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbnCode() {
        return isbnCode;
    }

    public void setIsbnCode(String isbnCode) {
        this.isbnCode = isbnCode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Book && isbnCode.equals(((Book) obj).isbnCode);
    }

    @Override
    public int hashCode() {
        return isbnCode.hashCode();
    }

    @Override
    public String toString() {
        return String.format("{isbn %s, author %s, title %s ...}",isbnCode, author, title);
    }

    public static Builder builder(){
        return new Builder();
    }

    public YearMonth getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(YearMonth publishedYear) {
        this.publishedYear = publishedYear;
    }

    public static class Builder{

        private Book book = new Book();

        public Builder title(String title){
            book.setTitle(title);
            return this;
        }

        public Builder author(String author){
            book.setAuthor(author);
            return this;
        }

        public Builder isbn(String isbnCode){
            book.setIsbnCode(isbnCode);
            return this;
        }

        public Builder yearOfPublishing(int year, Month month){
            book.setPublishedYear(YearMonth.of(year, month));
            return this;
        }

        public Builder categories(String ... category){
            book.setCategories(Arrays.asList(category));
            return this;
        }

        public Builder publisher(String publisher){
            book.setPublisher(publisher);
            return this;
        }

        public Book build(){
            return this.book;
        }
    }
}
