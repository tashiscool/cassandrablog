package org.lacassandra.smooshyfaces.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseDAO<EntityType, IDType extends Serializable> {
    public Long countAll();

    public EntityType findById(final IDType id);

    public List<EntityType> findByIds(final Collection<IDType> ids);

    public EntityType save(final EntityType entity);

    public List<EntityType> save(final List<EntityType> entities);

    public void delete(final EntityType entity);
}
