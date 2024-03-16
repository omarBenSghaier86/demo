package com.storeapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Post.
 */
@Entity
@Table(name = "ingredients")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ingredients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "price", nullable = false)
    private Long price;
    @NotNull
    @Column(name = "qty", nullable = false)
    private Long qty;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "items")
   // @JsonIgnoreProperties(value = { "sandwich", "items" }, allowSetters = true)
    private Set<Sandwich> sandwich = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Set<Sandwich>  getSandwich() {
        return sandwich;
    }

    public void setSandwich(Set<Sandwich>  sandwich) {
        this.sandwich = sandwich;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredients that = (Ingredients) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(code, that.code) && Objects.equals(price, that.price) && Objects.equals(qty, that.qty) && Objects.equals(sandwich, that.sandwich);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, price, qty, sandwich);
    }

    @Override
    public String toString() {
        return "Ingredients{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", code='" + code + '\'' +
            ", price=" + price +
            ", qty=" + qty +
            ", sandwich=" + sandwich +
            '}';
    }
}
