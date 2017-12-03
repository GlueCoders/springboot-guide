package org.gluecoders.library.dao;

import org.gluecoders.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Anand_Rajneesh on 6/14/2017.
 */
@Repository
public interface BookDao extends JpaRepository<Book, String>, BookDaoCustom {

    Book findDistinctByIsbnCode(String isbnCode);
}
