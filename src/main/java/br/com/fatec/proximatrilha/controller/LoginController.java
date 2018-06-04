package br.com.fatec.proximatrilha.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.fatec.proximatrilha.model.User;
import br.com.fatec.proximatrilha.security.JwtUtils;
import br.com.fatec.proximatrilha.security.Login;
import br.com.fatec.proximatrilha.view.View;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
	
	@Autowired
    @Qualifier("authenticationManager")
    public AuthenticationManager auth;

    public void setAuth(AuthenticationManager auth) {
        this.auth = auth;
    }
	    
    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.User.class)
    @CrossOrigin(exposedHeaders = "Token")
    public User login(@RequestBody Login login, HttpServletResponse response) throws JsonProcessingException {
        Authentication credentials = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        User user = (User) auth.authenticate(credentials).getPrincipal();
        user.setPassword(null);
           response.setHeader("Token", JwtUtils.generateToken(user));
           return user;
    }    
	
}
