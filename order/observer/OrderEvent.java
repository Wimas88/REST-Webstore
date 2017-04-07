package order.observer;

public class OrderEvent {

	private Order order;
	private String actionType;
	
	public OrderEvent(Order order, String actionType) {
		super();
		this.order = order;
		this.actionType = actionType;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}