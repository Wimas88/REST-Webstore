package order.observer;

@SessionScoped
public class OrderNotifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void notifyShippingDepartment(@Observes(during=TransactionPhase.AFTER_SUCCESS) OrderEvent orderEvent){

		Order order = orderEvent.getOrder();

		// Just notifies a simple message
		// Originally used to alert the shipping and administration departments
		System.out.println(order.toString());
	}
}
