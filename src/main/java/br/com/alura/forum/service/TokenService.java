package br.com.alura.forum.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")
	private String expiracao;
	@Value("${forum.jwt.secret}")
	private String segredo;
	

	public String geraToken(Authentication autenticado) {
		Calendar calendar = Calendar.getInstance();
		Date agora = calendar.getTime();
		calendar.setTimeInMillis(agora.getTime() + Long.parseLong(expiracao));
		Date expirado = calendar.getTime();
		
		return Jwts.builder()
				.setIssuer("API Rest Fórum")
				.setSubject(autenticado.getPrincipal().toString())
				.setIssuedAt(agora)
				.setExpiration(expirado)
				.signWith(SignatureAlgorithm.HS256, segredo)
				.compact();
	}
}
