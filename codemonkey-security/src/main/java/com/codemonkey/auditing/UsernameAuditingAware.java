package com.codemonkey.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UsernameAuditingAware implements AuditorAware<String> {
	
    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null) {
            return Optional.empty();
        }
        if (ctx.getAuthentication() == null) {
            return Optional.empty();
        }
        if (ctx.getAuthentication().getPrincipal() == null) {
            return Optional.empty();
        }
        Object principal = ctx.getAuthentication().getPrincipal();
        return Optional.of(((User) principal).getUsername());
    }
}
