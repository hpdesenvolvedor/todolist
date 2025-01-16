package br.com.henriquenunes.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.henriquenunes.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Validar usuário
        var servletPath = request.getServletPath();
        if(servletPath.startsWith("/tasks/")) {

            var authorization = request.getHeader("Authorization");
            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            var autString = new String(authDecode);


            String[] credentials = autString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            System.out.println("Authorization:");
            System.out.println(username);
            System.out.println(password);


            // Validar usuário
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            }else {
                // Validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified){
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
                // Segue viagem

        }
        } else {
            filterChain.doFilter(request, response);
        }

    }
}
