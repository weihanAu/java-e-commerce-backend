package com.ecommerce.ecommerce.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id")
  @JsonIgnore
  private Cart cart;
  
  public void setTotalPrice(){
    this.totalPrice = this.unitPrice.multiply(new BigDecimal(this.quantity));
  }
}
