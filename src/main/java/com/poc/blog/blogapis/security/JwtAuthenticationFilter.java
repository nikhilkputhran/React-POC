package com.poc.blog.blogapis.security;

import com.poc.blog.blogapis.exceptions.GenericException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String requestToken=request.getHeader("Authorization");
            System.out.println(requestToken);
            String userName= null;
            String token =null;
            if(null!=request && null!=requestToken && requestToken.startsWith("Bearer")){
                token = requestToken.substring(7);

                try{
                    userName = jwtTokenHelper.getUsernameFromToken(token);
                }
                catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException e)
                {

                    System.out.println("Unable to get Jwt token");
                }
            } else{
                System.out.println("Jwt token does not begin with Bearer");
            }
            if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if(jwtTokenHelper.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }
                else {
                    System.out.println("Invalid jwt token");
                }

            }
            else{
                System.out.println("user is null or context is not null");
            }

            filterChain.doFilter(request,response);
        }

    }

