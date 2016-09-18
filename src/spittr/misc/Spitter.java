package spittr.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Entity
public class Spitter implements UserDetails {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;  
  
  @NotNull
  @Size(min=5, max=16, message="{username.size}")
  @Column(name="username")
  private String username;

  @NotNull
  @Size(min=5, max=25, message="{password.size}")
  @Column(name="password")
  private String password;
  
  @NotNull
  @Size(min=2, max=30, message="{firstName.size}")
  @Column(name="firstName")
  private String firstName;

  @NotNull
  @Size(min=2, max=30, message="{lastName.size}")
  @Column(name="lastName")
  private String lastName;
  
  @NotNull
  @Email
  @Column(name="email")
  private String email;

  public Spitter() {}
  
  public Spitter(String username, String password, String firstName, String lastName, String email) {
    this(null, username, password, firstName, lastName, email);
  }

  public Spitter(Long id, String username, String password, String firstName, String lastName, String email) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    //authorities.add(new SimpleGrantedAuthority("ROLE_SPITTER"));
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object that) {
    return EqualsBuilder.reflectionEquals(this, that, "firstName", "lastName", "username", "password", "email");
  }
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, "firstName", "lastName", "username", "password", "email");
  }
  
  public void addRole(String role) {
	authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
  }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
 @Transient
 private List<GrantedAuthority> authorities =
        new ArrayList<GrantedAuthority>();

}
