package order.ejb;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
@Interceptors(LogInterceptor.class)
public class OrderEjb implements OrderEjbLocal {

	// The context of this persistence will be defined in the Resources.java class
	@Inject
	private EntityManager em;

	// StoreEjb will perform the database calls for the other entities (such as user, cart)
	// It used to be an ejb for each entity, now merged into an unique ejb for this demo
	@Inject
	private StoreEjb storeEjb;

	@Inject
	private OrderLogEjb orderLogEjb;

	@Inject
	private Event<OrderEvent> orderEvent;

	// I have purposely chosen to use manual transactions for this demo
	// Automatic ones will be used in the OrderLogEjb
	@Resource
	private UserTransaction tx;

	@Override
	public List<Order> getAllOrders() throws Exception {

		try{
			tx.begin();
			String SQLOrder = "SELECT o FROM Order o";
			TypedQuery<Order> orderQuery = em.createQuery(SQLOrder, Order.class);
			List<Order> orderList = orderQuery.getResultList();
			tx.commit();
			return orderList;
		} 
		catch(Exception e){
			tx.rollback();
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public Ordine getOrderById(Long id) throws Exception {
		try{
			tx.begin();
			Order order = em.find(Order.class, id);
			tx.commit();
			return order;
		} 
		catch(Exception e){
			tx.rollback();
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public Order addNewOrder(Order order) throws Exception {
		try{
			tx.begin();
			order.setUser(storeEjb.getUserById(order.getUser().getId()));
			order.setAddress(storeEjb.getShippingAddressById(order.getAddress().getId()));

			for (Cart cart : order.getCart()){
				cart.setItem(storeEjb.getItemById(cart.getItem().getId()));
				cart.setOrderId(order.getId());
			}

			em.persist(order);	
			
			// Each new order will produce an event that will be absorbed by an observer
			orderEvent.fire(new OrderEvent(order, "POST"));	

			try{
				// This will persist a log on a different database
				orderLogEjb.createLog(order, "POST");
			} 
			catch (Exception e){
				System.out.println("Found an error while persisting on the Log database");
			}		
			tx.commit();
			return order;
		}	
		catch(Exception e){
			tx.rollback();
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public void deleteOrderById(Long id) throws Exception {
		try{
			tx.begin();
			Order order = em.find(Order.class, id);
			em.remove(order);

			try{
				orderLogEjb.createLog(order, "DELETE");
			} 
			catch (Exception e){
				System.out.println("Found an error while persisting on the Log database");
			}
			tx.commit();
		} 
		catch (Exception e){
			tx.rollback();
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
