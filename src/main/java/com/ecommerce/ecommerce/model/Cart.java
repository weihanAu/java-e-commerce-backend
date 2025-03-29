package com.ecommerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private BigDecimal totalAmount = BigDecimal.ZERO; 

  @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CartItem> items;

  @OneToMany
  @JoinColumn(name = "user_id")
  private User user;

   public void addItem(CartItem cartItemitem) {
        this.items.add(cartItemitem);
        cartItemitem.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem cartItemitem) {
        this.items.remove(cartItemitem);
        cartItemitem.setCart(null);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        this.totalAmount = items.stream().map(item -> {
            BigDecimal unitPrice = item.getUnitPrice();
            if (unitPrice == null) {
                return  BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    
}
