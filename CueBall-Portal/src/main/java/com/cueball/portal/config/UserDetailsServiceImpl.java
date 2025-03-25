package com.cueball.portal.config;


import com.cueballdb.model.User;
import com.cueballdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);


        System.out.println(":::::::::::::::::::::: " + user.getCreatedBy());
        System.out.println(":::::::::::::::::::::: " + user.getCreatedBy());
        System.out.println(":::::::::::::::::::::: " + user.getCreatedBy());
        System.out.println(":::::::::::::::::::::: " + user.getCreatedBy());
        System.out.println(":::::::::::::::::::::: " + user.getCreatedBy());


        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

        System.out.println(":::::::::::::::::::::: " + user.getModifyBy());
        System.out.println(":::::::::::::::::::::: " + user.getModifyBy());
        System.out.println(":::::::::::::::::::::: " + user.getModifyBy());
        System.out.println(":::::::::::::::::::::: " + user.getModifyBy());
        System.out.println(":::::::::::::::::::::: " + user.getModifyBy());
        System.out.println(":::::::::::::::::::::: " + user.getModifyBy());


        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }

}
