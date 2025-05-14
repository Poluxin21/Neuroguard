// Genérico RestController com o caminho base configurado
package org.atlas.neuroguard.infra.controllerutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericRestController<T, ID> implements RestApi<T> {

    protected final CrudService<T, ID> service;

    protected GenericRestController(CrudService<T, ID> service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable ID id) {
        return ResponseEntity.of(service.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<T> create(@RequestBody T entity) {
        return ResponseEntity.status(201).body(service.create(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        return ResponseEntity.ok(service.update(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
