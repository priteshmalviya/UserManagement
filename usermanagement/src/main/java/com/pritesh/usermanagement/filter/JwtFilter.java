package com.pritesh.usermanagement.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pritesh.usermanagement.service.UserDeatailsServiceImpl;
import com.pritesh.usermanagement.utils.Constants;
import com.pritesh.usermanagement.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDeatailsServiceImpl userDeatailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(Constants.AUTHORIZATION);
        String username = null;
        String jwt = null;
        log.info("Token: " + token);
        if (token != null && token.startsWith(Constants.BEARER)) {
            jwt = token.substring(7);
            username = jwtUtils.getUsernameFromToken(jwt);
        }
        log.info("Username: " + username);
        if (username != null) {
            UserDetails userDetails = this.userDeatailsServiceImpl.loadUserByUsername(username);
            if (jwtUtils.isTokenExpired(jwt)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
