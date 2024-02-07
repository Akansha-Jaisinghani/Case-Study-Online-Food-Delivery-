package com.foodapp.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.foodapp.service.UserService;
import com.foodapp.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JWTUtil jwtUtil;
    private final UserService userService;
    
    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
    	final String authHeader = request.getHeader("Authorization");
		String email ;
		String jwtToken ;
		
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwtToken = authHeader.substring(7);
		email = jwtUtil.extractUsername(jwtToken);
		// Once we get the token validate it.
		if(!StringUtils.isEmpty(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.userDetailsService().loadUserByUsername(email);
	         // if token is valid configure Spring Security to manually set
			// authentication
			if (jwtUtil.validateToken(jwtToken, userDetails)) {
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				//SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				context.setAuthentication(usernamePasswordAuthenticationToken);
				SecurityContextHolder.setContext(context);
			}
		}
		filterChain.doFilter(request, response);
		
	}

	
}
