package HealthDeclaration.service.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import HealthDeclaration.modal.entity.User;
import HealthDeclaration.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    IUserRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getByUsername(username);
        System.out.println(user.getPassword());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        List<String> roles = new ArrayList<>();
        roles.add(user.getRoleCode());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), encoder.encode(user.getPassword()),
                mapRoleToAuthorities(roles));
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthorities (List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
