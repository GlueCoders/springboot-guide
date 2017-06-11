package org.gluecoders.library.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/10/2017.
 */
public class Book implements Serializable {

    private String title;
    private String author;
    private int publishedYear;
    private List<String> categories;
    private String publisher;
    private long isbnCode;

    public Book() {
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

    public long getIsbnCode() {
        return isbnCode;
    }

    public void setIsbnCode(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Book && Long.compare(isbnCode, ((Book) obj).getIsbnCode()) == 0;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(isbnCode);
    }

    public static Builder builder(){
        return new Builder();
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

        public Builder isbn(long isbnCode){
            book.setIsbnCode(isbnCode);
            return this;
        }

        public Builder yearOfPublishing(int year){
            book.setPublishedYear(year);
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
