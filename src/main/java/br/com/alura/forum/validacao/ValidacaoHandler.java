package br.com.alura.forum.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.alura.forum.validacao.dto.ValidacaoDTO;

@RestControllerAdvice
public class ValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<ValidacaoDTO> handler(MethodArgumentNotValidException exception){
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		List<ValidacaoDTO> erros = new ArrayList<>();
		
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			erros.add(new ValidacaoDTO(e.getField(), mensagem));
		});
		
		return erros;
	}

}
