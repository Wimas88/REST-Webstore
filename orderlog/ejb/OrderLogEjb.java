package orderlog.ejb;

@Stateless
@LocalBean
public class OrderLogEjb implements OrderLogEjbLocal {

	// Annotated with the custom qualifier interface, it will persist on the Log database
	@Inject
	@LogEm
	private EntityManager em;

	@Override
	public List<OrderLog> getAllOrderLogs() {
		String SQLOrderLog = "SELECT l FROM LogOrdine l";
		TypedQuery<OrderLog> logQuery = em.createQuery(SQLOrderLog, OrderLog.class);
		List<OrderLog> logsList = logQuery.getResultList();
		return logsList;
	}

	@Override
	public OrderLog getLogById(Long id) {
		OrderLog log = em.find(OrderLog.class, id);
		return log;
	}

	@Override
	public void deleteOrderLogById(Long id) {
		OrderLog log = em.find(OrderLog.class, id);
		em.remove(log);	
	}
	
	//This method is called from the OrderEjb. Should this persistence fail, it will NOT affect the OrderEjb one
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createLog(Order order, String action) throws Exception {
		try{
			OrderLog log = new OrderLog();
			log.setUsername(order.getUser().getUsername());
			log.setCreationDate(new Date());
			log.setAction("Action type: " + action);
			em.persist(log);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
