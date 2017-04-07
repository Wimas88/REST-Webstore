package store;

@Path("store")
public class Store {

	@EJB
	private OrderEjb orderEjb;
	
	@EJB
	private OrderLogEjb orderLogEjb;

	@Inject
	private ModelMapper modelMapper;

	/********************************************************************************/
	//ORDERS
	
	@GET
	@Path("/orders/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllOrders(){
		try{
			List<Order> orderList = orderEjb.getAllOrders();
			List<OrderDto> orderListDto = new ArrayList<>();

			for (Order order : orderList){
				OrderDto orderDto = modelMapper.map(order, OrderDto.class);
				orderListDto.add(orderDto);
			}		
			return Response.status(Status.OK).entity(orderListDto).build();
		}
		catch (Exception e){
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/orders/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showOrderById(@PathParam ("id") Long id){
		try{
			Order order = orderEjb.getOrderById(id);
			OrderDto orderDto = modelMapper.map(order, OrderDto.class);
			return Response.status(Status.OK).entity(orderDto).build();
		}
		catch (Exception e){
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/orders/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postNewOrder(@Valid Order order){
		try{	
			OrderEjb.addNewOrder(order);
			OrderDto orderDto = modelMapper.map(order, OrderDto.class);
			return Response.status(Status.OK).entity(orderDto).build();
		}
		catch (Exception e){
			return Response.status(Status.BAD_REQUEST).build();
		}

	}

	@DELETE
	@Path("/orders/{id}")
	public Response removeOrderById(@PathParam ("id") Long id){
		try{
			orderEjb.deleteOrderById(id);
			return Response.status(Status.OK).entity("Order removed.").build();
		}
		catch (Exception e){
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	/********************************************************************************/
	//LOGS
	
	@GET
	@Path("/logs")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllLogs(){

		try{
			List<OrderLog> logList = orderLogEjb.getAllOrderLogs();
			List<OrderLogDto> logListDto = new ArrayList<>();

			for (OrderLog log : logList){
				OrderLogDto logDto = modelMapper.map(log, OrderLogDto.class);
				logListDto.add(logDto);
			}		
			return Response.status(Status.OK).entity(logListDto).build();
		}
		catch(Exception e){
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/logs/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showLogById(@PathParam ("id") Long id){

		try{
			OrderLog log = orderLogEjb.getLogById(id);
			OrderLogDto logDto = modelMapper.map(log, OrderLog.class);
			return Response.status(Status.OK).entity(logDto).build();
		}
		catch(Exception e){
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("/logs/{id}")
	public Response removeLogById(@PathParam ("id") Long id){
		try{
			orderLogEjb.deleteOrderLogById(id);
			return Response.status(Status.OK).entity("Log n." + id + " removed.").build();
		}
		catch (Exception e){
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

}
