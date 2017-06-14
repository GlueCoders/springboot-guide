package org.gluecoders.library.dao;

import org.gluecoders.library.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Anand_Rajneesh on 6/14/2017.
 */
@Repository
public interface BookDao extends MongoRepository<Book, String> {

    Book findDistinctByIsbnCode(long isbnCode);
}
