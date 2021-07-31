package com.MasterTweetsAnalysis.service.impl;


import com.MasterTweetsAnalysis.model.User;
import com.MasterTweetsAnalysis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    @Transactional
    public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {  Set<GrantedAuthority> grantedAuthorities = new HashSet< >();
            grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    grantedAuthorities);}
        else { throw new UsernameNotFoundException("User not found");}

    }
}
