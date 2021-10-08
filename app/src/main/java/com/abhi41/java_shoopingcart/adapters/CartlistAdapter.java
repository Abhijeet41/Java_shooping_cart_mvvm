package com.abhi41.java_shoopingcart.adapters;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi41.java_shoopingcart.databinding.SingleCartItemBinding;
import com.abhi41.java_shoopingcart.models.CartItem;

public class CartlistAdapter extends ListAdapter<CartItem, CartlistAdapter.CartVH> {
    private CartInterface cartInterface;
    public CartlistAdapter(CartInterface cartInterface) {
        super(CartItem.itemCallback);
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SingleCartItemBinding binding = SingleCartItemBinding.inflate(layoutInflater, parent, false);

        return new CartVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
        holder.binding.setCartItem(getItem(position));
        holder.binding.executePendingBindings();
    }

    class CartVH extends RecyclerView.ViewHolder {
        SingleCartItemBinding binding;

        public CartVH(@NonNull SingleCartItemBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
            binding.deleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartInterface.deleteItem(getItem(getAbsoluteAdapterPosition()));
                }
            });

            binding.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int quantity = position + 1;
                    if (quantity == getItem(getAbsoluteAdapterPosition()).getQuantity()){
                        return;
                    }
                    cartInterface.changeQuantity(getItem(getAbsoluteAdapterPosition()),quantity);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public interface CartInterface{
        void deleteItem(CartItem cartItem);
        void changeQuantity(CartItem cartItem, int quantity);
    }
}
