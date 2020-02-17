package pl.sda.blog.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

	private UserRegistry userRegistry;

	public UserRestController(UserRegistry userRegistry) {
		this.userRegistry = userRegistry;
	}

	@PostMapping("/rest/users")
	public void registerUser(@RequestBody RegisterUserCommand command) {
		userRegistry.register(command);
	}
}
