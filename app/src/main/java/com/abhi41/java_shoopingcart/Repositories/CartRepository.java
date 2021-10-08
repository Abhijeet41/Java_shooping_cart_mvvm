package com.abhi41.java_shoopingcart.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.java_shoopingcart.models.CartItem;
import com.abhi41.java_shoopingcart.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();
    private MutableLiveData<Double> mutableTotalprice = new MutableLiveData<>();

    public LiveData<List<CartItem>> getCart() {

        if (mutableCart.getValue() == null) {
            intitCart();
        }
        return mutableCart;
    }

    public void intitCart() {
        mutableCart.setValue(new ArrayList<>());
        calculateCartTotal();
    }

    public boolean addItemToCart(Product product) {
        if (mutableCart.getValue() == null) {
            intitCart();
        }

        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());

        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                if (cartItem.getQuantity() == 5) {
                    return false;
                }

                int index = cartItemList.indexOf(cartItem);
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItemList.set(index, cartItem);

                mutableCart.setValue(cartItemList);
                calculateCartTotal();
                return true;
            }
        }

        CartItem cartItem = new CartItem(product, 1);
        cartItemList.add(cartItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
        return true;
    }

    public void removeItemFromCart(CartItem cartItem) {

        if (mutableCart.getValue() == null) {
            return;
        }

        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        cartItemList.remove(cartItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
    }

    public void changeQuantity(CartItem cartItem, int quantity) {
        if (mutableCart.getValue() == null) {
            return;
        }

        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        CartItem updatedItem = new CartItem(cartItem.getProduct(), quantity);
        cartItemList.set(cartItemList.indexOf(cartItem), updatedItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
    }

    // get total price
    public LiveData<Double> getTotalPrice() {
        if (mutableTotalprice == null) {
            mutableTotalprice.setValue(0.0);
        }

        return mutableTotalprice;
    }

    //calculate cart total value

    private void calculateCartTotal() {
        if (mutableCart.getValue() == null) {
            return;
        }

        double total = 0.0;
        List<CartItem> cartItemList = mutableCart.getValue();
        for (CartItem cartItem : cartItemList) {
            total+= cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        mutableTotalprice.setValue(total);
    }
}
