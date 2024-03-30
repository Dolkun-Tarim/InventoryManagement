package com.dolkuntarim.product;

import com.dolkuntarim.user.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public List<Product> listAllProductsByUser() {
        String uuid = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (hasRole("ADMIN")) {
                return productRepository.findAll();
            }
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            uuid = myUserDetails.getUserUUID();
        }
        return productRepository.findAllByUser(uuid);
    }

    public void save(Product product) {
        String uuid = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            uuid = myUserDetails.getUserUUID();
        }
        if (!hasRole("ADMIN")) {
            product.setUser_uuid(uuid);
        } else {
            String originalUserUUID = product.getUser_uuid();
            product.setUser_uuid(originalUserUUID);

        }
        productRepository.save(product);
    }

    public Product get(Long id) {
        return productRepository.findById(id).get();
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
    private boolean hasRole(String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }
}
