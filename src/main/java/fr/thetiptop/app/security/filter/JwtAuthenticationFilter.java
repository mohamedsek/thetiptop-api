package fr.thetiptop.app.security.filter;


import fr.thetiptop.app.security.service.AppJwtService;
import fr.thetiptop.app.security.service.AppUserDetails;
import fr.thetiptop.app.security.service.AppUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    protected final Log LOG = LogFactory.getLog(getClass());

    @Autowired
    private AppJwtService appJwtService;
    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = retrieveAccessToken(request);
            if (StringUtils.isNotBlank(token)) {
                String uid = appJwtService.getUid(token);
                AppUserDetails userDetails = (AppUserDetails) appUserDetailsService.loadUserByUid(uid);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
            LOG.error("Couldn't create authentication security context", exception);
        }
        filterChain.doFilter(request, response);
    }

    private String retrieveAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7, authorizationHeader.length());
        }
        return StringUtils.EMPTY;
    }

    public void setAppJwtFacade(AppJwtService appJwtService) {
        this.appJwtService = appJwtService;
    }

    public void setAppUserDetailsService(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }
}
