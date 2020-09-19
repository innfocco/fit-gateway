package jc.gateway.ctrl;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jc.gateway.entity.Behavior;
import jc.gateway.svc.BehaviorService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(path = "/behaviors", produces = MediaType.APPLICATION_JSON_VALUE)
public class BehaviorController {
	
	private BehaviorService svc;

	public BehaviorController(BehaviorService svc) {
		this.svc = svc;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Behavior> create(@RequestBody Behavior behavior) {
		log.info("Creating behavior: {} ", behavior);
		behavior.setId(UUID.randomUUID().toString());
		svc.create(behavior);
		return ResponseEntity.ok(behavior);
	}

	@GetMapping
	public ResponseEntity<Iterable<Behavior>> readAll() {
		log.info("reading all behaviors");
		return ResponseEntity.ok(svc.readAll());
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<Behavior> readOne(@PathVariable("id") String id) {
		log.info("reading behavior: {} ", id);
		return ResponseEntity.ok(svc.read(id));
	}

	@PutMapping
	public ResponseEntity<Behavior> update(@RequestBody Behavior behavior) {
		log.info("updating behavior: {} ", behavior);
		svc.update(behavior);
		return ResponseEntity.ok(behavior);
	}

	@DeleteMapping(path="/{id}")
	public void delete(@PathVariable("id") String id) {
		log.info("Deleting behavior: {} ", id);
		svc.delete(id);
	}

}
