package com.ceica.securityspring.service;

import com.ceica.securityspring.config.AppConfig;
import com.ceica.securityspring.model.Authority;
import com.ceica.securityspring.model.User;
import com.ceica.securityspring.repository.AuthorityRepository;
import com.ceica.securityspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private AppConfig appConfig;
    @Autowired
    public UserService(UserRepository userRepository,AuthorityRepository authorityRepository,AppConfig appConfig) {
        this.userRepository = userRepository;
        this.authorityRepository=authorityRepository;
        passwordEncoder=new BCryptPasswordEncoder();
        this.appConfig=appConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre de usuario: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getAuthorities())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return grantedAuthorities;
    }

    public void crearUsuario(User user,MultipartFile foto) {
        //Encriptamos password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       User newUser=userRepository.save(user);
        // Guarda la imagen en el servidor
        String fotoNombre = guardarFotoServidor(foto, newUser.getId() + "-" + newUser.getUsername()+".jpg");
        // Guarda el nombre de la foto en la base de datos
        newUser.setFoto(fotoNombre);
        userRepository.save(newUser);
       Authority authority=new Authority();
       authority.setAuthority("USER");
       authority.setUser_id(newUser.getId());
        authorityRepository.save(authority);
    }

    private String guardarFotoServidor(MultipartFile file, String fileName) {
        String uploadDir =appConfig.getUserImageDirectory() ; // Cambia esto por la ruta real de tu carpeta
        String filePath = uploadDir + File.separator + fileName;
        try {
            file.transferTo(new File(filePath));
        } catch ( IOException e) {
            e.printStackTrace();
            // Manejar la excepción apropiadamente
        }
        return fileName;
    }
}
