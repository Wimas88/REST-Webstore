package com.k2.libreria.resources;

@ApplicationScoped
public class Resources {

	@PersistenceUnit(unitName = "store.pu")
	private EntityManagerFactory emfStore;
	
	@PersistenceUnit(unitName = "order_log.pu")
	private EntityManagerFactory emfLog;
	
	@Produces
	@RequestScoped
	@Default
	public EntityManager createStoreEntityManager() {
		return emfStore.createEntityManager();
	}
	
	@Produces
	@RequestScoped
	@LogEm
	public EntityManager createLogEntityManager() {
		return emfLog.createEntityManager();
	}
}