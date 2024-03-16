package com.storeapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Blog.
 */
@Entity
@Table(name = "sandwich")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sandwich implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "cost", nullable = false)
    private Long cost;

    @ManyToMany(fetch = FetchType.LAZY)
    @Basic(fetch=FetchType.LAZY)

    @JoinTable(name = "rel_sandwich__ingredient", joinColumns = @JoinColumn(name = "sandwich_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
   // @JsonIgnoreProperties(value = { "sandwich" }, allowSetters = true)
    private Set<Ingredients> items = new HashSet<>();
    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;
    public Sandwich(String name, Set<Ingredients> items) {
        this.name = name;
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Sandwich() {
        this.name = name;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Set<Ingredients> getItems() {
        return items;
    }

    public void setItems(Set<Ingredients> items) {
        this.items = items;
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

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sandwich sandwich = (Sandwich) o;
        return Objects.equals(id, sandwich.id) && Objects.equals(name, sandwich.name) && Objects.equals(cost, sandwich.cost) && Objects.equals(items, sandwich.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost, items);
    }

    @Override
    public String toString() {
        return "Sandwich{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", cost=" + cost +
            ", items=" + items +
            '}';
    }
}
