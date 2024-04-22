package com.jmiko.reservations.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Column(name = "name")
    private String name;

    @Column(name = "nip")
    private String nip;

    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
    private Set<ServiceCategory> categories = new HashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<User> users = new HashSet<>();

    public Vendor(Long vendorId, String name, String nip, Set<User> users) {
        this.vendorId = vendorId;
        this.name = name;
        this.nip = nip;
        this.users = users;
    }

    public Vendor() {
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Set<ServiceCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<ServiceCategory> categories) {
        this.categories = categories;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
