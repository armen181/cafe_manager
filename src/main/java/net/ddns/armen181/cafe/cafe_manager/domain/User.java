package net.ddns.armen181.cafe.cafe_manager.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import net.ddns.armen181.cafe.cafe_manager.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class User implements UserDetails {
    //=================  Fields =======================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eMail", nullable = false)
    private String eMail;

    @Column(name = "firsName", nullable = false)
    private String firsName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "userPassword", nullable = false)
    private String userPassword;


    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CafeTable> cafeTables = new HashSet<>();


    //=================  Fields =======================

    //=================  Constructor =======================
    public User() {

    }
    //=================  Constructor =======================

    public User addCafeTable(CafeTable cafeTable) {
        cafeTable.setUser(this);
        cafeTable.setUserName(this.eMail);
        this.cafeTables.add(cafeTable);
        return this;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Lists.newArrayList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return getUserPassword();
    }

    @Override
    public String getUsername() {
        return getEMail();
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
