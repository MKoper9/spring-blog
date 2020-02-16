package pl.sda.blog.rest;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("repositoryUserDetailsService")
public class RepositoryDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public RepositoryDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
		// in real application we should create such a user e.g. in
		// migration scripts
		BlogUser testUser = new BlogUser("test_user",
			// the password is test_user_password
			"$2a$10$FApO0z5/bkTM8PSVkHyw3ONavCXTyBE5RDOcjN1iyDZl4WydZb6ga", "USER");
		this.userRepository.save(testUser);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
			.map(blogUser -> new User(blogUser
				.getUsername(), blogUser.getPassword(), Arrays
				.asList(new SimpleGrantedAuthority("ROLE_" + blogUser
					.getRole()))))
			.orElseThrow(() -> new UsernameNotFoundException(String
				.format("User %s not found in the database.",
					username)));
	}
}
