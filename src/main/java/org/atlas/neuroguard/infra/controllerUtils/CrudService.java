package org.atlas.neuroguard.infra.controllerUtils;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    Optional<T> findById(ID id);
    T create(T entity);
    T update(ID id, T entity);
    void delete(ID id);
}
