package edu.sjsu.android.cs_160_project;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.sjsu.android.cs_160_project.databinding.OrdersSingleItemBinding;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder>
{
    private ArrayList<MenuEntry> orders;

    public CustomerOrderAdapter(ArrayList<MenuEntry> orders)
    {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OrdersSingleItemBinding row = OrdersSingleItemBinding.inflate(layoutInflater);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuEntry current = orders.get(position);
        holder.binding.orderItemName.setText(current.getName());
        holder.binding.orderItemPrice.setText("$" + current.getPrice());
        holder.binding.descriptionTxt.setText(current.getDescription());
        Glide.with(holder.itemView.getContext()).load(Uri.parse(current.getImage_url())).into(holder.binding.orderItemImage);


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        protected final OrdersSingleItemBinding binding;
        public ViewHolder(OrdersSingleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
