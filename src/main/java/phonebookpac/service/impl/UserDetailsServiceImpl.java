package phonebookpac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import phonebookpac.model.Account;
import phonebookpac.service.AccountService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by macbookair on 02.04.16.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        if (accountService == null){
            System.out.println("null repository");
        }
        Account account = accountService.findByLogin(login);

        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("user"));

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(account.getLogin(),
                        account.getPassword(),
                        roles);
        return userDetails;
    }

}
