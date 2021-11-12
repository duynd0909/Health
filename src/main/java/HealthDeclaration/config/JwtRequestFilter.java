package HealthDeclaration.config;

import java.io.IOException;
import java.util.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HealthDeclaration.service.serviceImpl.JwtUserDetailsService;
import HealthDeclaration.vo.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(false);
        String requestMethod = request.getMethod();
        if ("options".equalsIgnoreCase(requestMethod)) {
            chain.doFilter(request, response);
        } else {
            // JWT Token is in the form "Bearer token". Remove Bearer word
            // and get
            // only the Token
            boolean validateToken = false;
            if (requestTokenHeader != null
                    && requestTokenHeader.startsWith("Bearer ")) {
                final int subStringIndex = 7;
                jwtToken = requestTokenHeader.substring(subStringIndex);
                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                    validateToken =
                            jwtTokenUtil.validateToken(jwtToken, username);
                } catch (Exception e) {
                    responseMessage.setSuccess(false);
                }
            } else {
                responseMessage.setSuccess(false);
            }
            // Once we get the token validate it.
            if (username != null && validateToken) {
                UserDetails userDetails =
                        this.jwtUserDetailsService.loadUserByUsername(username);
                // if token is valid configure Spring Security to manually set
                // authentication
                if (jwtTokenUtil.validateToken(jwtToken,
                        userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken
                            usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null,
                                    userDetails.getAuthorities());
                    Map<String, String> details = new HashMap<>();
                    details.put("ip", request.getRemoteAddr());
                    details.put("accessToken", jwtToken);
                    usernamePasswordAuthenticationToken.setDetails(details);
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(usernamePasswordAuthenticationToken);
                    responseMessage.setSuccess(true);
                }
            }
            if (!responseMessage.isSuccess()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    final private Set<String> skipUrls = new HashSet<>(Arrays.asList("/api/login"));
    final private AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        return skipUrls.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

}