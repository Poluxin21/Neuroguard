package org.atlas.neuroguard.infra.controllerutils;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface RestApi<T> {
    ResponseEntity<List<T>> findAll();
    ResponseEntity<T> findById(UUID id);
    ResponseEntity<T> create(T entity);
    ResponseEntity<T> update(UUID id, T entity);
    ResponseEntity<Void> delete(UUID id);
}