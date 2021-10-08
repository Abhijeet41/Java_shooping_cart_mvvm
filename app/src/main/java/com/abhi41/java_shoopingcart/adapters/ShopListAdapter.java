package com.abhi41.java_shoopingcart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi41.java_shoopingcart.databinding.SingleShopRowBinding;
import com.abhi41.java_shoopingcart.models.Product;

public class ShopListAdapter extends ListAdapter<Product, ShopListAdapter.ViewHolder> {


    ShopInterface shopInterface;

    public ShopListAdapter(ShopInterface shopInterface) {
        super(Product.itemCallback);
        this.shopInterface = shopInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleShopRowBinding shopRowBinding = SingleShopRowBinding.inflate(inflater,parent,false);
        shopRowBinding.setShopInterface(shopInterface);
        return new ViewHolder(shopRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = getItem(position);
        holder.shopRowBinding.setProduct(product);
        holder.shopRowBinding.executePendingBindings();



    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleShopRowBinding shopRowBinding;

        public ViewHolder(SingleShopRowBinding shopRowBinding) {
            super(shopRowBinding.getRoot());
            this.shopRowBinding = shopRowBinding;


        }
    }

    public interface ShopInterface{
        void addItem(Product product);
        void onItemClick(Product product);
    }

}
