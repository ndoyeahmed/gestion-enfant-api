package com.gestion.stock.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
    /**
     * Methode permettant de retourner le nom d'utilisateur de l'utilisateur connecté
     *
     * @return String
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        // Just return a string representing the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }


        return Optional.ofNullable(authentication.getName()).filter(s -> !s.isEmpty());
    }
}
