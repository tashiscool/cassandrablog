package org.lacassandra.smooshyfaces.persistence.cassandra;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import org.apache.commons.lang.NotImplementedException;
import org.lacassandra.smooshyfaces.entity.BaseEntity;
import org.lacassandra.smooshyfaces.persistence.BaseDAO;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class BaseCassandraDAO<EntityType extends BaseEntity, IDType extends Serializable> implements BaseDAO<EntityType, IDType> {
    protected AstyanaxContext<Keyspace> context;

    public AstyanaxContext<Keyspace> getContext() {
        return context;
    }

//    @Inject
    public void setContext(AstyanaxContext<Keyspace> context) {
        this.context = context;
    }

    protected BaseCassandraDAO() {

    }

    @Override
    public Long countAll() {
        throw new NotImplementedException();
    }

    @Override
    public EntityType findById(final IDType id) {
        throw new NotImplementedException();
    }

    @Override
    public List<EntityType> findByIds(final Collection<IDType> ids) {
        throw new NotImplementedException();
    }

    @Override
    public EntityType save(final EntityType entity) {
        throw new NotImplementedException();
    }

    @Override
    public List<EntityType> save(List<EntityType> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(final EntityType entity) {
        throw new NotImplementedException();
    }


    public Keyspace getKeyspace() {
        return context.getEntity();
    }
}
