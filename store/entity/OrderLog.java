package store.entity;

@Entity
@Table(name="order_log")
public class OrderLog {

	@Id
	@SequenceGenerator(name="log_seq", sequenceName="log_seq", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="log_seq")
	private Long id;
	
	@Column(name="date")
	private Date creationDate;

	@Column
	private String username;
	
	@Column(length=200)
	private String action;
	
	public OrderLog() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
