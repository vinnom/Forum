package br.com.alura.forum.controller.form;

import br.com.alura.forum.model.Topico;

public class AtualizaTopicoForm {

	private String titulo;
	private String mensagem;

	public AtualizaTopicoForm(String titulo, String mensagem) {
		this.titulo = titulo;
		this.mensagem = mensagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public Topico atualiza(Topico topico) {
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);

		return topico;
	}

}
