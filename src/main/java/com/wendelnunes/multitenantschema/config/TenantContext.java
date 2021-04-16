package com.wendelnunes.multitenantschema.config;

public class TenantContext {

	final public static String DEFAULT_TENANT = "CLIENTE_1";

	private static final ThreadLocal<String> CURRENT_TENANT = ThreadLocal.withInitial(() -> DEFAULT_TENANT);

	public static String getCurrenttenant() {
		return CURRENT_TENANT.get();
	}

	public static void setCurrenttenant(String tenant) {
		CURRENT_TENANT.set(tenant);
	}

	public static void clear() {
		CURRENT_TENANT.remove();
	}
}