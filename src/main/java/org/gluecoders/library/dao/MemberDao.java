package org.gluecoders.library.dao;

import org.gluecoders.library.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface MemberDao extends JpaRepository<Member, String> {
}
