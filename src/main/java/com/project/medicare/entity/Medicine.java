package com.project.medicare.entity;
import jakarta.persistence.*;

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    int quantity;
    double price;
    String seller;
    String description;
    @Embedded
    Image image;
    @Column(columnDefinition = "boolean default true")
    boolean active;

    public Medicine() {

    }

    public Medicine(int id, String name, int quantity, double price, String seller, String description, Image image, boolean active) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.seller = seller;
        this.description = description;
        this.image = image;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", seller='" + seller + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", active=" + active +
                '}';
    }
}

