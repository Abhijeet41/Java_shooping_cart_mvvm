package com.abhi41.java_shoopingcart.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhi41.java_shoopingcart.R;
import com.abhi41.java_shoopingcart.adapters.CartlistAdapter;
import com.abhi41.java_shoopingcart.databinding.FragmentCartBinding;
import com.abhi41.java_shoopingcart.models.CartItem;
import com.abhi41.java_shoopingcart.viewmodel.ShopViewModel;

import java.util.List;


public class CartFragment extends Fragment implements CartlistAdapter.CartInterface {
    private static final String TAG = "CartFragment";
    ShopViewModel shopViewModel;
    CartlistAdapter cartlistAdapter;
    FragmentCartBinding binding;
    NavController controller;

    public CartFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = Navigation.findNavController(view);

        cartlistAdapter = new CartlistAdapter(this);
        binding.cartRecyclerView.setAdapter(cartlistAdapter);
        binding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);

        observers();

        onClickEvent();

    }



    private void observers() {
        shopViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartlistAdapter.submitList(cartItems);
                binding.placeOrderButton.setEnabled(cartItems.size() > 0);
            }
        });

        shopViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.orderTotalTextView.setText("Total: $ " + aDouble.toString());
            }
        });
    }
    private void onClickEvent() {

        binding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_cartFragment_to_orderFragment);
            }
        });

    }

    @Override
    public void deleteItem(CartItem cartItem) {

        shopViewModel.removeItemFromCart(cartItem);
        Log.d(TAG, "deleteItem:" + cartItem.getProduct().getName());
    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
        shopViewModel.changeQuantity(cartItem, quantity);
    }
}