package ru.satird.communal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "account")
    private Integer account;


    @JsonIgnoreProperties(value = {"children"})
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "service")
    private Service service;

    public User() {
    }

    public User(String fullname, Integer account) {
        this.fullname = fullname;
        this.account = account;
    }

    public User(String fullname, Integer account, Service service) {
        this.fullname = fullname;
        this.account = account;
        this.service = service;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
