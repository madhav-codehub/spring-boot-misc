package com.luv4code.springbootlogging.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/home")
    public String home(
            @AuthenticationPrincipal OidcUser principle,
            Model model
    ) {
        List<String> rolesList = principle.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        log.info("Roles List: {}", rolesList);
        model.addAttribute("username", principle.getPreferredUsername());
        model.addAttribute("email", principle.getEmail());
        model.addAttribute("name", principle.getFullName());
//        model.getAttribute("roles",);
        return "home";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
