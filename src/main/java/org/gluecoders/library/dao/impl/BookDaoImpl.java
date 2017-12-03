package org.gluecoders.library.dao.impl;

import org.gluecoders.library.dao.BookDaoCustom;
import org.gluecoders.library.models.Book;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/17/2017.
 */
@Component
public class BookDaoImpl implements BookDaoCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookDaoImpl.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Book> findBooks(List<String> categories, String author, String title, String publishedYear) {
        /*Query query = buildSearchQuery(categories, author, title, publishedYear);
        LOGGER.debug("Query built for findBooks {}", query);
        return mongoTemplate.find(query, Book.class);*/
        return Collections.emptyList();
    }

    /*private Query buildSearchQuery(List<String> categories, String author, String title, String publishedYear) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(author, title);
        Criteria criteria = new Criteria();
        if (categories != null && categories.size() > 0) {
            criteria.and("categories").in(categories);
        }
        if (StringUtils.hasText(publishedYear)) {
            criteria.and("publishedYear.year").is(Integer.parseInt(publishedYear));
        }
        if(author != null || title != null){
            return TextQuery.queryText(textCriteria).addCriteria(criteria);
        }else {
            return Query.query(criteria);
        }
    }*/

}
