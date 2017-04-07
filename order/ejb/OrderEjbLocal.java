package order.ejb;

@Local
public interface OrderEjbLocal {

	public List<Order> getAllOrders() throws Exception;
	public Order getOrderById(Long id) throws Exception;
	public Order addNewOrder(Order order) throws Exception;
	public void deleteOrderById(Long id) throws Exception;
}
