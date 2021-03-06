package com.emusicstore.service;

import com.emusicstore.model.Cart;
import com.emusicstore.model.CartItem;

public interface CartItemService {

    void addCartItem(CartItem cartItem);
    void removeCartItem(CartItem cartItem);
    void clearCart(Cart cart);
    CartItem getCartItemByProductId(int productId, int cartId);
    void updateCartItemQuantity(CartItem cartItem);
}
