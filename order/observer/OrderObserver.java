package order.observer;

@SessionScoped
public class OrderObserver implements Serializable {

	private static final long serialVersionUID = 1L;
 	
 	@Inject
 	private EntityManager em;
 	
 	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
 	public void controllaAzione(@Observes(during=TransactionPhase.BEFORE_COMPLETION) OrderEvent orderEvent) throws RuntimeException {
 		
		Order order = orderEvent.getOrder();
		
		switch(orderEvent.getActionType()) {
			case("POST"):
				finishOrderOperations(order);
				break;
		}		
	}
 	
	private void finishOrderOperations(Order order) {
		for (Cart cart : order.getCart()){
			
			//Calculates Total
			order.setTotal(order.getTotal() + cart.getItemPrice());
			
			// Operazioni sui prodotti
			cart.getItem().setQuantity(cart.getItem().getQuantity() - cart.getOrderedItemQuantity());
			
			if(cart.getItem().getQuantity() <= 0){
				throw new RuntimeException("NO MORE ITEMS IN WAREHOUSE") ;
			}
			
			order.setOrderDate(new java.util.Date());
			em.merge(cart.getItem());
		}
	}
}
