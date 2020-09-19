package jc.gateway.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jc.gateway.entity.Behavior;

@Repository
public interface BehaviorRepository extends CrudRepository<Behavior, String> {}
