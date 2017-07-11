package org.gluecoders.library.services;

import org.gluecoders.library.dao.CredentialsDao;
import org.gluecoders.library.dao.MemberDao;
import org.gluecoders.library.exceptions.ResourceAlreadyExistsException;
import org.gluecoders.library.models.Credentials;
import org.gluecoders.library.models.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
@Component
public class DefaultRegistrationService implements  RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRegistrationService.class);

    private final CredentialsDao credentialsDao;
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultRegistrationService(CredentialsDao credentialsDao, MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.credentialsDao = credentialsDao;
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Member register(Credentials credentials) throws ResourceAlreadyExistsException {
        LOGGER.info("Registering {} in library system", credentials);
        Credentials existingRecord = credentialsDao.findDistinctByUsername(credentials.getUsername());
        if(existingRecord == null) {
            credentials.setSaltedPwd(passwordEncoder.encode(credentials.getPwd()));
            credentials.setRole("USER");
            credentials.setCreatedOn(Instant.now());
            credentialsDao.save(credentials);
            Member member = new Member();
            member.setEmail(credentials.getUsername());
            memberDao.save(member);
            LOGGER.info("Member {} saved", member);
            return member;
        }else{
            throw new ResourceAlreadyExistsException("Member with given username "+credentials.getUsername() + " already exists");
        }
    }
}
