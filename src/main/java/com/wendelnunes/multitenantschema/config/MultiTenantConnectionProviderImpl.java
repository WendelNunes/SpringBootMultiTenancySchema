package com.wendelnunes.multitenantschema.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4443972025023470598L;

	private final DataSource dataSource;

	public MultiTenantConnectionProviderImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public boolean isUnwrappableAs(@SuppressWarnings("rawtypes") Class unwrapType) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		final Connection connection = this.getAnyConnection();
		try {
			connection.createStatement().execute("USE " + tenantIdentifier);
		} catch (SQLException e) {
			throw new HibernateException("Não foi possível alterar para o schema [" + tenantIdentifier + "]", e);
		}
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		try (connection) {
			connection.createStatement().execute("USE " + TenantContext.DEFAULT_TENANT);
		} catch (SQLException e) {
			throw new HibernateException("Não foi possível se conectar ao schema padrão", e);
		}
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return true;
	}
}