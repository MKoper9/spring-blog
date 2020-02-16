package pl.sda.blog.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class BlogUser {

	public BlogUser(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	@Id
	@GeneratedValue
	private UUID id;
	private String username;
	private String password;
	private String role;
}
