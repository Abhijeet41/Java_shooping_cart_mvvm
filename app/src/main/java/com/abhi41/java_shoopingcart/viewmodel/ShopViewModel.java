package com.abhi41.java_shoopingcart.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.java_shoopingcart.Repositories.CartRepository;
import com.abhi41.java_shoopingcart.Repositories.ShopRepository;
import com.abhi41.java_shoopingcart.models.CartItem;
import com.abhi41.java_shoopingcart.models.Product;

import java.util.List;

public class ShopViewModel extends AndroidViewModel {

    ShopRepository shopRepository;
    CartRepository cartRepository;
    MutableLiveData<Product> mutableProduct = new MutableLiveData<>();

    public ShopViewModel(@NonNull Application application) {
        super(application);

        shopRepository = new ShopRepository();
        cartRepository = new CartRepository();
    }

    public LiveData<List<Product>> getProducts() {
        return shopRepository.getProducts();
    }

    public void setProduct(Product product) {
        mutableProduct.setValue(product);
    }

    public LiveData<Product> getProduct() {
        return mutableProduct;
    }

    public LiveData<List<CartItem>> getCart() {
        return cartRepository.getCart();
    }

    public boolean addItemToCart(Product product) {
        return cartRepository.addItemToCart(product);
    }

    public void removeItemFromCart(CartItem cartItem) {
        cartRepository.removeItemFromCart(cartItem);
    }

    public void changeQuantity(CartItem cartItem, int quantity) {
        cartRepository.changeQuantity(cartItem, quantity);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepository.getTotalPrice();
    }

    public void resetcart(){
        cartRepository.intitCart();
    }
}
