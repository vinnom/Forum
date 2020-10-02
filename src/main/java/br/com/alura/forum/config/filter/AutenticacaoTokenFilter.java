package br.com.alura.forum.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.model.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import br.com.alura.forum.service.TokenService;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	public AutenticacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recuperaToken(request);
		configuraAutenticacaoViaToken(token);

		filterChain.doFilter(request, response);
	}

	private void configuraAutenticacaoViaToken(String token) {
		if (tokenService.valida(token)) {
			Usuario usuario = usuarioRepository.findById(tokenService.getIdUsuario(token)).get();

			UsernamePasswordAuthenticationToken autenticador = 
					new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(autenticador);
		}
	}

	private String recuperaToken(HttpServletRequest request) {
		if (request.getHeader("Authorization") == null || request.getHeader("Authorization").isEmpty()) {
			return null;
		}
		String[] conteudoCabecalho = request.getHeader("Authorization").split(" ");
		if (!conteudoCabecalho[0].equals("Bearer")) {
			return null;
		}

		return conteudoCabecalho[1];
	}

}
