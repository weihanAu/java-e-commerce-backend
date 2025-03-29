package com.ecommerce.ecommerce.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.ProductRepository.ProductRepository;
import com.ecommerce.ecommerce.dto.OrderDto;
import com.ecommerce.ecommerce.enums.OrderStatus;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.model.OrderItem;
import com.ecommerce.ecommerce.model.Product;

import lombok.RequiredArgsConstructor;

import com.ecommerce.ecommerce.responsitory.Order.OrderResponsitory;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

  private final OrderResponsitory orderResponsitory;
  private final ModelMapper modelMapper;
  private final ProductRepository productRepository;

  @Override
  public Order placeOrder(Long userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'placeOrder'");
  }

  @Override
  public OrderDto getOrder(Long orderId) {
     return orderResponsitory.findById(orderId)
                .map(this :: convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
  }

  @Override
  public List<OrderDto> getUserOrders(Long userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUserOrders'");
  }

  private OrderDto convertToDto(Order order) {
      return modelMapper.map(order, OrderDto.class);
  }

  private Order createOrder(Cart cart){
    Order order = new Order();
    //set the user
    order.setOrderStatus(OrderStatus.PENDING);
    order.setOrderDate(LocalDate.now());
    return order;
  }

  private List<OrderItem> createOrderItems(Order order,Cart cart){
    return cart.getItems().stream().map(cartItem -> {
      Product product = cartItem.getProduct();
      product.setInventory(product.getInventory() - cartItem.getQuantity());
      productRepository.save(product);
      return new OrderItem(
        order,
        product,
        cartItem.getQuantity(),
        cartItem.getUnitPrice()
      );
    }).toList();
  }

  private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList){

    return orderItemList
          .stream()
          .map(item->item.getPrice()
          .multiply(new BigDecimal(item.getQuantity())))
          .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
