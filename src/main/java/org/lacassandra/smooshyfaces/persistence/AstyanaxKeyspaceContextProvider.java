package org.lacassandra.smooshyfaces.persistence;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.Slf4jConnectionPoolMonitorImpl;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.model.ConsistencyLevel;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

public class AstyanaxKeyspaceContextProvider{

    protected int thriftPort;
    protected String seedHosts;
    protected int maxBlockedThreadsPerHost;
    protected int maxTimeoutCount;
    protected int maxTimeoutWhenExhausted;
    protected int maxConnectionsPerHost;
    protected int maxPendingConnectionsPerHost;
    protected int maxConnectinos;
    protected String clusterName;
    protected String poolName;
    protected String keyspaceName;
	
	public int getThriftPort() {
		return thriftPort;
	}

	public void setThriftPort(int thriftPort) {
		this.thriftPort = thriftPort;
	}

	public String getSeedHosts() {
		return seedHosts;
	}

	public void setSeedHosts(String seedHosts) {
		this.seedHosts = seedHosts;
	}

	public int getMaxBlockedThreadsPerHost() {
		return maxBlockedThreadsPerHost;
	}

	public void setMaxBlockedThreadsPerHost(int maxBlockedThreadsPerHost) {
		this.maxBlockedThreadsPerHost = maxBlockedThreadsPerHost;
	}

	public int getMaxTimeoutCount() {
		return maxTimeoutCount;
	}

	public void setMaxTimeoutCount(int maxTimeoutCount) {
		this.maxTimeoutCount = maxTimeoutCount;
	}

	public int getMaxTimeoutWhenExhausted() {
		return maxTimeoutWhenExhausted;
	}

	public void setMaxTimeoutWhenExhausted(int maxTimeoutWhenExhausted) {
		this.maxTimeoutWhenExhausted = maxTimeoutWhenExhausted;
	}

	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public int getMaxPendingConnectionsPerHost() {
		return maxPendingConnectionsPerHost;
	}

	public void setMaxPendingConnectionsPerHost(int maxPendingConnectionsPerHost) {
		this.maxPendingConnectionsPerHost = maxPendingConnectionsPerHost;
	}

	public int getMaxConnectinos() {
		return maxConnectinos;
	}

	public void setMaxConnectinos(int maxConnectinos) {
		this.maxConnectinos = maxConnectinos;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getKeyspaceName() {
		return keyspaceName;
	}

	public void setKeyspaceName(String keyspaceName) {
		this.keyspaceName = keyspaceName;
	}

	public AstyanaxContext<Keyspace> get() {
        AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forCluster(getClusterName())
                .forKeyspace(getKeyspaceName())
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
                        .setDiscoveryType(NodeDiscoveryType.TOKEN_AWARE)
                        .setDefaultReadConsistencyLevel(ConsistencyLevel.CL_QUORUM)
                        .setDefaultWriteConsistencyLevel(ConsistencyLevel.CL_QUORUM)
                )
                .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl(getPoolName())
                        .setPort(getThriftPort())
                        .setSeeds(getSeedHosts())
                        .setMaxBlockedThreadsPerHost(getMaxBlockedThreadsPerHost())
                        .setMaxTimeoutCount(getMaxTimeoutCount())
                        .setMaxTimeoutWhenExhausted(getMaxTimeoutWhenExhausted())
                        .setMaxConnsPerHost(getMaxConnectionsPerHost())
                        .setMaxPendingConnectionsPerHost(getMaxPendingConnectionsPerHost())
                        .setMaxConns(getMaxConnectinos())
                )
                .withConnectionPoolMonitor(new Slf4jConnectionPoolMonitorImpl())
                .buildKeyspace(ThriftFamilyFactory.getInstance());
        context.start();
        return context;
    }

}
