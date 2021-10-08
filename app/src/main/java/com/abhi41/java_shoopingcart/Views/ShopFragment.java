package com.abhi41.java_shoopingcart.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhi41.java_shoopingcart.R;
import com.abhi41.java_shoopingcart.viewmodel.ShopViewModel;
import com.abhi41.java_shoopingcart.adapters.ShopListAdapter;
import com.abhi41.java_shoopingcart.databinding.FragmentShopBinding;
import com.abhi41.java_shoopingcart.models.Product;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class ShopFragment extends Fragment implements ShopListAdapter.ShopInterface {

    private static final String TAG = "ShopFragment";

    private FragmentShopBinding binding;
    private ShopListAdapter shopListAdapter;
    private ShopViewModel shopViewModel;

    NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopListAdapter = new ShopListAdapter(this);

        navController = Navigation.findNavController(view);

        binding.shopRecyclerView.setAdapter(shopListAdapter);

        binding.shopRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));

        binding.shopRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.HORIZONTAL));

        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);


        observers();

    }

    private void observers() {
        shopViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                shopListAdapter.submitList(products);

            }
        });

    }

    @Override
    public void addItem(Product product) {
        Log.d(TAG, "addItem: " + product.toString());

        boolean isAdded = shopViewModel.addItemToCart(product);
        if (isAdded) {
            Snackbar.make(requireView(), product.getName() + "added to cart.", Snackbar.LENGTH_LONG)
                    .setAction("checkout", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navController.navigate(R.id.action_shopFragment_to_cartFragment);
                        }
                    }).show();
        } else {
            Snackbar.make(requireView(), "already have the max quantity in cart", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onItemClick(Product product) {
        Log.d(TAG, "onItemClick: " + product.toString());
        shopViewModel.setProduct(product);
        navController.navigate(R.id.action_shopFragment_to_prouctDetailFragment);
    }


}