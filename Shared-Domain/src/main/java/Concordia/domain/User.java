
package concordia.domain;


import java.io.Serializable;

/*---------------------------------------------------------------------------------------
 * The 'User' class is used to hold details for said users of the system 
 ---------------------------------------------------------------------------------------*/

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User implements Serializable{
			public User(int userId, int companyId, String username, String password) {
				this.userId = userId;
				this.username = username;
				this.password = password;
				this.company = new Company();
				this.company.setCompanyId(companyId);
			}
		public int getCompanyId() {
			return company != null ? company.getCompanyId() : 0;
		}
	@Id
	@Column(name = "user_id")
	private int userId;

	@Column(name = "user_name", length = 25)
	private String username;

	@Column(name = "user_password", length = 25, nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;


	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User(int userId, String username, String password, Company company) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.company = company;
	}

	public User() {}


	public void ListUsers(){
		// ...
	}

	public void changePassw(){
		// ...
	}

	public void login(){
		// ...
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
