package com.School.SchoolValleyProject.Rest;


import com.School.SchoolValleyProject.Constants.ValleyPublicConst;
import com.School.SchoolValleyProject.Model.Contact;
import com.School.SchoolValleyProject.Model.Response;
import com.School.SchoolValleyProject.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(path="/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*")
public class RestContactController {

    @Autowired
    ContactRepository contactRepository;
    JavaMailSender javaMailSender;
    @GetMapping("/getMessagesByStatus")
    public List<Contact> getMessagesByStatus(@RequestParam(name = "status") String status)
    {
        return contactRepository.findByStatus(status);
    }

    @GetMapping("/getAllMsgsByStatus")
    public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact){
        if(null != contact && null != contact.getStatus()){
            return contactRepository.findByStatus(contact.getStatus());
        }else{
            return null;
        }
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom,
                                           @Valid @RequestBody  Contact contact)
    {
        log.info(String.format("Header invocation from=%s",invocationFrom));
        contactRepository.save(contact);
        Response response=new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message Saved Successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved","true")
                .body(response);

    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity)
    {
        HttpHeaders headers=requestEntity.getHeaders();
        headers.forEach((key,value)->
        {
            log.info(String.format("Header '%s'= %s",key,value.stream().collect(Collectors.joining("|"))));
    });
        Contact contact=requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());
        Response response= new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message Successfully Deleted");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response>colseMsg(@RequestBody Contact contactReq)
    {
        Response response=new Response();
        Optional<Contact>contact=contactRepository.findById(contactReq.getContactId());
        if(contact.isPresent()) {
            if (contact.get().getStatus().equals("Close")) {

                response.setStatusCode("208");
                response.setStatusMsg("Status is already closed");
                return ResponseEntity
                        .status(HttpStatus.ALREADY_REPORTED)
                        .body(response);
            }
            else
            {

            contact.get().setStatus(ValleyPublicConst.CLOSE);
            contactRepository.save(contact.get());

        }

        }
        else
        {
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Contact ID received");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        response.setStatusCode("200");
        response.setStatusMsg("Message Successfully Closed");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


}
