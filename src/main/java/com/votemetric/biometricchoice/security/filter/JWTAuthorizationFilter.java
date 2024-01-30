package com.votemetric.biometricchoice.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.votemetric.biometricchoice.interfaces.IAdminService;
import com.votemetric.biometricchoice.interfaces.IUserService;
import com.votemetric.biometricchoice.modules.admin.AdminDTO;
import com.votemetric.biometricchoice.modules.user.UserDTO;
import com.votemetric.biometricchoice.security.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final IAdminService adminService;
    private final IUserService userService;

    public JWTAuthorizationFilter(IAdminService adminService, IUserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (header == null || !header.startsWith(SecurityConstants.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(SecurityConstants.BEARER, "");
        String email = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
        try {
            AdminDTO admin = adminService.loadUserByEmail(email);

            if (isAuthorized(admin.getRole(), request)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("You don't have permission to access this resource");
                response.getWriter().flush();
            }
        } catch (Exception e) {
            try {
                UserDTO user = userService.getUserByEmail(email);
                if (user != null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("You don't have permission to access this resource");
                    response.getWriter().flush();
                }
                //System.out.println("User is not found"
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    private boolean isAuthorized(String role, HttpServletRequest request) {
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();

        if (role.equals("ADMIN")) {
            return true;
        } else if (role.equals("USER")) {
            if (requestMethod.equals("GET") && requestURI.startsWith("/fingerprints/getFingerprint")) {
                return true;
            }
            if (requestMethod.equals("POST") && requestURI.startsWith("/voters/register")) {
                return true;
            }
            if (requestMethod.equals("GET") && requestURI.startsWith("/devices")) {
                return true;
            }
            if (requestMethod.equals("GET") && requestURI.startsWith("/fingerprints/nextFingerprint")) {
                return true;
            }
        }
        if (requestURI.startsWith("/user/user-role/")) {
            return true;
        }
        return true;
    }
}
