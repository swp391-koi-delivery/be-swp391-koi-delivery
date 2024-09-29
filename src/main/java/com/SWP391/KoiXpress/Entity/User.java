package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;



@Getter
@Setter
@Entity
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Enumerated(EnumType.STRING)
    Role role;

    @NotBlank(message = "username can not be blank!")
    @Pattern(regexp = "(MGR|SLE|DLV|CTS)+\\d{6}", message = "UserName is not available!")
    String username;

    @NotBlank(message = "password can not be blank!")
    @Size(min=6, message = "password at least 6 character!")
    String password;

    @NotBlank(message = "fullname can not be blank!")
    @Size(min = 1, message = "fullName at least 1 character!")
    @Column(unique = true)
    String fullname;

    @Column(length = 200)
    String image;

    @Column(length = 200)
    String address;
    @NotBlank(message = "phone can not be blank!")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone!")
    @Column(unique = true)
    String phone;

    @NotBlank(message = "email can not be blank!")
    @Pattern(regexp = "[A-Za-z]+@gmail\\.com", message = "Invalid email!")
    String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(value = 0, message = "at least 0")
    long loyaltyPoint;

    @JsonIgnore
    Boolean userstatus = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
