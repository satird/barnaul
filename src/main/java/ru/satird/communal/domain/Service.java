package ru.satird.communal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SERVICE")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "parent")
    Service parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Service> children = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    List<User> users = new ArrayList<>();

    public Service() {
    }

    public Service(Integer id) {
        this.id = id;
    }

    public Service(String name) {
        this.name = name;
    }

    public Service(String name, Service parent) {
        this.name = name;
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Service getParent() {
        return parent;
    }

    public void setParent(Service parent) {
        this.parent = parent;
    }

    public void setChildren(List<Service> children) {
        this.children = children;
    }

    public List<Service> getChildren() {
        return children;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
