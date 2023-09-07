package com.bank.app.infrastructure.token;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bank.app.entity.administrator.repository.AdministratorRepository;
import com.bank.app.entity.client.repository.ClientRepository;
import com.bank.app.entity.official.repository.OfficialRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {
  @Autowired
  private TokenService tokenService;
  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private OfficialRepository officialRepository;
  @Autowired
  private AdministratorRepository administratorRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
      throws ServletException, IOException {
    String token;
    var authorization = req.getHeader("Authorization");

    if (authorization != null) {
      token = authorization.replace("Bearer", "");
      var subject = this.tokenService.getSubject(token);
      var role = this.tokenService.getIssuer(token);
      var user = this.clientRepository.findByCpf(subject);

      switch (role) {

        case ("ROLE_OFFICIAL"): {
          logger.info("OFFICIAL");
          user = this.officialRepository.findByCpf(subject);
          break;
        }
        case ("ROLE_ADM"): {
          user = this.administratorRepository.findByCpf(subject);
          break;
        }
        case ("ROLE_BOSS"): {
          break;
        }
        default: {
          break;
        }
      }

      var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(req, res);
  }

}
