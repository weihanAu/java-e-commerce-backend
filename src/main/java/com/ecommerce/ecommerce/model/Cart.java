package com.ecommerce.ecommerce.model;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.annotation.Id;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private BigDecimal totalAmount = BigDecimal.ZERO; 

  @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CartItem> items;

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
