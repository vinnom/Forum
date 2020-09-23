package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;

public class TopicoForm {
	
	@NotNull
	@NotEmpty
	@Length(min = 5, max = 120)
	private String titulo;
	@NotNull
	@NotEmpty
	@Length(min = 5, max = 1000)
	private String mensagem;
	@NotNull
	@NotEmpty
	@Length(min = 5, max = 120)
	private String nomeCurso;
	
	public TopicoForm(String titulo, String mensagem, String nomeCurso) {
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.nomeCurso = nomeCurso;
	}

	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}

	public Topico converte(CursoRepository cursoRepository) {
		Curso curso = cursoRepository.findByNome(this.nomeCurso);
		return new Topico(this.titulo, this.mensagem, curso);
	}

}
