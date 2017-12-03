package org.gluecoders.library.dao;

import org.gluecoders.library.models.Book;

import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/17/2017.
 */
public interface BookDaoCustom {

    List<Book> findBooks(List<String> categories, String author, String title, String publishedYear);

}
