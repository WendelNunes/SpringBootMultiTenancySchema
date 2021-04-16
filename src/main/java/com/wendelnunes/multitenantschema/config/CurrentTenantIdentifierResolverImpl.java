package com.wendelnunes.multitenantschema.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		return TenantContext.getCurrenttenant();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}