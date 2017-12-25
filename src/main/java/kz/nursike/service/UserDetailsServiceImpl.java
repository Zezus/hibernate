package kz.nursike.service;

import kz.nursike.dao.UserDAO;
import kz.nursike.model.Role;
import kz.nursike.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by СадвакасовР on 25.12.2017.
 */
public class UserDetailsServiceImpl  implements UserDetailsService{

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s)throws UsernameNotFoundException{
        User user = userDAO.findByUsername(s);
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<GrantedAuthority>();
        if (user == null) throw new UsernameNotFoundException("User not found");
        for (Role role: user.getRoles()){
            grantedAuthoritySet.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), grantedAuthoritySet);
    }
}
