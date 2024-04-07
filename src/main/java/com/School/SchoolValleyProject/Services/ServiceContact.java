package com.School.SchoolValleyProject.Services;

import com.School.SchoolValleyProject.Constants.ValleyPublicConst;
import com.School.SchoolValleyProject.Model.Contact;
import com.School.SchoolValleyProject.WebConfig.ValleySchoolProps;
import com.School.SchoolValleyProject.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Slf4j
@Service
//@RequestScope
//@SessionScop
@ApplicationScope
public class ServiceContact {


    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    ValleySchoolProps valleySchoolProps;

    public boolean saveMessage(Contact contact){
        boolean isSaved = false;
        contact.setStatus(ValleyPublicConst.OPEN);
        Contact savedContact = contactRepository.save(contact);
        if(null != savedContact && savedContact.getContactId()>0) {
            isSaved = true;
        }
        return isSaved;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir){

          int pageSize=valleySchoolProps.getPageSize();
          if(null!=valleySchoolProps.getContact() && null!=valleySchoolProps.getContact().get("pageSize"))
        {
            pageSize=Integer.parseInt(valleySchoolProps.getContact().get("pageSize").trim());
        }
        Pageable pageable= PageRequest.of(pageNum-1,pageSize,sortDir.equals("asc")? Sort.by(sortField).ascending():
                Sort.by(sortField).descending());
        Page<Contact>msgPage=contactRepository.findByStatusWithQuery(ValleyPublicConst.OPEN,pageable);
        return msgPage;
    }




    public boolean UpdateMsgStatus(int contactId){
        boolean isUpdated = false;
//        Optional<Contact> contact = contactRepository.findById(contactId);
//        contact.ifPresent(contact1 -> {
//            contact1.setStatus(ValleyPublicConst.CLOSE);
//        });
        int updatedContact = contactRepository.updateMsgStatusNative(ValleyPublicConst.CLOSE,contactId);//save(contact.get());
//        if(null != updatedContact && updatedContact.getUpdatedBy()!=null) {
//            isUpdated = true;
//        }

        if(updatedContact>0)
        {
            isUpdated = true;
        }
        return isUpdated;
    }

}
