package com.ecolink.spring.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore

    private Startup startup;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDate creationDate;

    
    @ManyToMany
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "id_product"), inverseJoinColumns = @JoinColumn(name = "id_category"))
    private List<Category> categories;

    public Product(Startup startup, String name, String description, BigDecimal price, LocalDate creationDate) {
        this.startup = startup;
        this.name = name;
        this.description = description;
        this.price = price;
        this.creationDate = creationDate;
    }

    public void addCategory(Category category) {
        category.getProducts().add(this);
        this.categories.add(category);
    }

}
