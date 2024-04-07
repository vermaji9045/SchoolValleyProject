package com.School.SchoolValleyProject.security;

import com.School.SchoolValleyProject.Model.Person;
import com.School.SchoolValleyProject.Model.Roles;
import com.School.SchoolValleyProject.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Profile("!prod")
public class ValleySchoolNonSecurity implements AuthenticationProvider {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email=authentication.getName();
        String pwd=authentication.getCredentials().toString();
        Person person=personRepository.readByEmail(email);

        if(null!=person &&person.getPerson_id()>0 )
        {
            return new UsernamePasswordAuthenticationToken(email,null, getGrantedAuthorities(person.getRoles()));
        }
        else
        {
            return null;
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Roles roles) {
        List<GrantedAuthority>grantedAuth=new ArrayList<>();
        grantedAuth.add(new SimpleGrantedAuthority("ROLE_"+roles.getRoleName()));
        return grantedAuth;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
