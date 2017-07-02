package org.gluecoders.library.rest;

import org.gluecoders.library.exceptions.ResourceAlreadyExistsException;
import org.gluecoders.library.exceptions.ValidationException;
import org.gluecoders.library.models.Credentials;
import org.gluecoders.library.models.Member;
import org.gluecoders.library.rest.helper.Validator;
import org.gluecoders.library.services.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path="/unsecured")
public class Registration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Registration.class);

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private Validator validator;

    @PostMapping(path = "/register")
    public ResponseEntity<Member> register(@RequestBody Credentials credentials) throws ResourceAlreadyExistsException, ValidationException {
        LOGGER.info("Received registration request for {}",credentials);
        validator.validate(credentials);
        Member member = registrationService.register(credentials);
        LOGGER.info("Member {} registered ",credentials);
        return ResponseEntity.ok(member);
    }

}
