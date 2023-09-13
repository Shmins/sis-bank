package com.bank.app.infrastructure.token;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bank.app.usecase.administrator.AdministratorService;
import com.bank.app.usecase.boss.BossService;
import com.bank.app.usecase.client.ClientService;
import com.bank.app.usecase.official.OfficialService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {
  @Autowired
  private TokenService tokenService;
  @Autowired
  private ClientService clientRepository;
  @Autowired
  private OfficialService officialRepository;
  @Autowired
  private AdministratorService administratorService;
  @Autowired
  private BossService bossService;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
      throws ServletException, IOException {
    String token;
    var authorization = req.getHeader("Authorization");

    if (authorization != null) {
      token = authorization.replace("Bearer", "");
      var subject = this.tokenService.getSubject(token);
      var role = this.tokenService.getIssuer(token);

      switch (role) {
        case ("ROLE_CLIENT"): {
          var user = this.clientRepository.getClientByCpf(subject);
          var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(auth);
          break;
        }
        case ("ROLE_OFFICIAL"): {
          UserDetails user = this.officialRepository.getOfficialByCpf(subject);

          if (user.isEnabled()) {
            var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
          } else {
            throw new IllegalAccessError("Funcionário não autorizado");
          }
          break;
        }
        case ("ROLE_ADM"): {
          var user = this.administratorService.getAdmByCpf(subject);

          var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(auth);
          break;
        }
        case ("ROLE_BOSS"): {
          var user = this.bossService.getBossByCpf(subject);
          var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(auth);
          break;
        }
        default: {
          break;
        }
      }

    }

    filterChain.doFilter(req, res);
  }

}
