package pl.sda.blog.rest;

import lombok.Data;

@Data
public class RegisterUserCommand {

	private String username;
	private String password;
}
