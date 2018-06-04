package br.com.fatec.proximatrilha.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fatec.proximatrilha.model.User;
import br.com.fatec.proximatrilha.repository.UserRepository;

@Service("securityService")
public class SecurityServiceProvider implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * @param usuarioRepo the usuarioRepo to set
	 */
	public void setUsuarioRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
	
}
