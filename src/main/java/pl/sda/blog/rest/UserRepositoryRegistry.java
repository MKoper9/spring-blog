package pl.sda.blog.rest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryRegistry implements UserRegistry {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserRepositoryRegistry(UserRepository userRepository,
				      PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void register(RegisterUserCommand command) {
		BlogUser newUser = new BlogUser(command
			.getUsername(), passwordEncoder
			.encode(command.getPassword()), "USER");
		userRepository.save(newUser);
	}
}
