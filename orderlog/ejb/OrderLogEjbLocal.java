package orderlog.ejb;

@Local
public interface OrderLogEjbLocal {

	public List<OrderLog> getAllOrderLogs();
	public OrderLog getLogById(Long id);
	public void createLog(Order order, String action) throws Exception;
	public void deleteOrderLogById(Long id);
}
