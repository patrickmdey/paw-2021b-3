package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

// TODO: why did we change to controller advice and injection?
// Now the ModelAttribute does not work and we have to manually add it to all the models we want to have the current user?
@ControllerAdvice
public class LoggedUserAdvice {
    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User loggedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principal = auth.getPrincipal();
        if (auth.isAuthenticated()) {
            String email;
            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
                return userService.findByEmail(email).orElse(null);
            } else {
                email = principal.toString();
                return null;
            }
        } else {
            return null;
        }
    }
}
