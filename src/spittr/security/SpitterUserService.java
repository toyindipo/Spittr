package spittr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.
                                                   UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import spittr.misc.Spitter;
import spittr.data.SpitterRepository;

@Service
public class SpitterUserService implements UserDetailsService {
  private final SpitterRepository spitterRepository;
  @Autowired
  public SpitterUserService(SpitterRepository spitterRepository) {
     this.spitterRepository = spitterRepository;
  }
  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    Spitter spitter = spitterRepository.findByUsername(username);
     if (spitter != null) {
       return spitter;
     }
     throw new UsernameNotFoundException(
    		    "User '" + username + "' not found.");
  }
}