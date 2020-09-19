package jc.gateway.svc;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jc.gateway.entity.Behavior;
import jc.gateway.repo.BehaviorRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BehaviorService {
	
	private BehaviorRepository repo;

	public BehaviorService(BehaviorRepository repo) {
		this.repo=repo;
	}

	public void create(Behavior behavior) {
		repo.save(behavior);
		log.info("Behavior Saved! {}", behavior);
	}
	
	public Behavior read(String id) {
		try {
			Optional<Behavior> bhv = repo.findById(id);
			log.info("Behavior Found! {}", id);
			return bhv.get();
		} catch (NoSuchElementException e) {
			log.info("Behavior NOT Found! {}", id);
			return null;
		}
	}
	
	public void update(Behavior behavior) {
		repo.save(behavior);
		log.info("Behavior Updated! {}", behavior);
	}
	
	public void delete(String id) {
		repo.deleteById(id);
		log.info("Behavior Deleted! {}", id);
	}
	
	public Iterable<Behavior> readAll() {
		log.info("Finding all Behaviors!");
		return repo.findAll();
	}
	
}
