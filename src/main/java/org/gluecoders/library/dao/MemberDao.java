package org.gluecoders.library.dao;

import org.gluecoders.library.models.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
@Repository
public interface MemberDao extends MongoRepository<Member, String> {
}
