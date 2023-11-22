package com.dattp.authservice.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    public Role(Long id, String name, boolean isEnable) {
        this.id = id;
        this.name = name.toUpperCase();
        this.isEnable = isEnable;
    }

    public Role() {
    }

    @Column(name = "id") @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_enable", nullable = false)
    private boolean isEnable;

    @ManyToMany(
        mappedBy = "roles"
    )
    @Fetch(value = FetchMode.SELECT)
    @JsonIgnore
    private List<User> users;

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", isEnable=" + isEnable + "]";
    }
}
