package com.ll.hereispaw.domain.member.member.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ll.hereispaw.domain.member.mypet.entity.MyPet;
import com.ll.hereispaw.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Column(unique = true, length = 30)
    private String username;

    private String password;

    @Column(length = 30)
    private String nickname;

    @Column(unique = true, length = 50)
    private String apiKey;

    @OneToMany
    @JsonManagedReference
    private List<MyPet> myPets;

    public boolean isAdmin() {
        return "admin".startsWith(username);
    }

    public boolean isManager() {
        return "manager".startsWith(username);
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public Member(long id, String username, String nickname) {
        this.setId(id);
        this.username = username;
        this.nickname = nickname;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthoritiesAsStringList()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public List<String> getAuthoritiesAsStringList() {
        List<String> authorities = new ArrayList<>();

        if (isAdmin())
            authorities.add("ROLE_ADMIN");

        if (isManager())
            authorities.add("ROLE_MANAGER");

        return authorities;
    }
}