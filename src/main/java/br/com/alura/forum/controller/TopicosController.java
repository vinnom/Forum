package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalheTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.AtualizaTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicosRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicosRepository topicosRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public List<TopicoDTO> lista(String nomeCurso) {
		if (nomeCurso == null) {
			return TopicoDTO.converte(topicosRepository.findAll());
		}
		return TopicoDTO.converte(topicosRepository.findByCurso_Nome(nomeCurso));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDTO> publica(@RequestBody @Valid TopicoForm topicoForm,
			UriComponentsBuilder uriBuilder) {
		Topico topico = topicoForm.converte(cursoRepository);
		topicosRepository.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<DetalheTopicoDTO> detalha(@PathVariable Long id) {
		Optional<Topico> optional = topicosRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = optional.get();
			return ResponseEntity.ok(new DetalheTopicoDTO(topico));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<DetalheTopicoDTO> atualiza(@PathVariable Long id,
			@RequestBody AtualizaTopicoForm topicoForm) {
		Optional<Topico> optional = topicosRepository.findById(id);
		if (optional.isPresent()) {
			Topico topicoDesatualizado = optional.get();
			Topico topicoAtualizado = topicoForm.atualiza(topicoDesatualizado);
			return ResponseEntity.ok(new DetalheTopicoDTO(topicoAtualizado));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<?> remove(@PathVariable Long id) {
		Optional<Topico> optional = topicosRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = optional.get();
			topicosRepository.delete(topico);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
