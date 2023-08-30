package com.logicea.cards.configs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicea.cards.dtos.auth.LogInRequest;
import com.logicea.cards.dtos.auth.LogInResponse;
import com.logicea.cards.entities.users.User;
import com.logicea.cards.helper.ResponseStatus;
import com.logicea.cards.helper.dto.APIResponse;
import com.logicea.cards.repositories.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class CustomFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;


    public CustomFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("email");
        String password = request.getParameter("password");

        LogInRequest logInRequest = null;
        try {
            var credentialsBody = IOUtils.toString(request.getReader());
            if (credentialsBody != null && !credentialsBody.isEmpty()) {
                logInRequest = new ObjectMapper().readValue(credentialsBody, LogInRequest.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (logInRequest != null) {
            username = logInRequest.getEmail();
            password = logInRequest.getPassword();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User principalUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

//        int expiresIn = 25,920,000;
        int expiresIn = 3600000 * 18;
        var dateOfExpiry = new Date(System.currentTimeMillis() + expiresIn);
        String accessToken = JWT.create().withSubject(principalUser.getUsername())
                .withExpiresAt(dateOfExpiry)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", principalUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        Optional<User> optionalUser = userRepository.findByEmail(principalUser.getUsername());
        User user = optionalUser.get();

        LogInResponse logInResponse = new LogInResponse();
        logInResponse.setAccessToken(accessToken);
        logInResponse.setExpiresIn(expiresIn);
        logInResponse.setName(user.getName());
        logInResponse.setEmail(user.getEmail());
        logInResponse.setPhoneNumber(user.getPhone());
        logInResponse.setUserId(user.getId());
        logInResponse.setUserGroup(
                new LogInResponse.UserGroupDetails(user.getUserType().getId(), user.getUserType().getName(),
                        user.getUserType().getDescription()));

        response.setContentType(APPLICATION_JSON_VALUE);
        APIResponse apiResponse = new APIResponse<>(ResponseStatus.SUCCESS.getResponseCode(), ResponseStatus.SUCCESS.getDescription(), logInResponse);
        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String error = failed.getLocalizedMessage();

        var disabled = error.equalsIgnoreCase("User is disabled");

        Map<String, Object> map = new HashMap<>();
        map.put("code", disabled ? ResponseStatus.DISABLED.getResponseCode() : ResponseStatus.LOGIN_FAILURE.getResponseCode());
        map.put("description", error);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        APIResponse apiResponse = new APIResponse<>(ResponseStatus.ERROR.getResponseCode(), disabled ? ResponseStatus.DISABLED.getDescription() : ResponseStatus.LOGIN_FAILURE.getDescription(), map);

        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }
}