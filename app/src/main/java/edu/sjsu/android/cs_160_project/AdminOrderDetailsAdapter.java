package edu.sjsu.android.cs_160_project;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.sjsu.android.cs_160_project.databinding.AdminOrderDetailSingleItemBinding;

public class AdminOrderDetailsAdapter extends RecyclerView.Adapter<AdminOrderDetailsAdapter.ViewHolder>
{
    private ArrayList<MenuEntry> orders;
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");


    public AdminOrderDetailsAdapter(ArrayList<MenuEntry> orders)
    {
        this.orders = orders;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate (= create) a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdminOrderDetailSingleItemBinding row = AdminOrderDetailSingleItemBinding.inflate(inflater);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuEntry temp = orders.get(position);

        holder.itemNameTxt.setText(temp.getName());
        holder.itemDescriptionTxt.setText(temp.getDescription());
        holder.itemPriceTxt.setText("$ " + decimalFormat.format(temp.getPrice()));

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    // Inner calsee
    public class ViewHolder extends RecyclerView.ViewHolder {
        protected final AdminOrderDetailSingleItemBinding binding;
        private TextView itemNameTxt;
        private TextView itemDescriptionTxt;
        private TextView itemPriceTxt;
        public ViewHolder(AdminOrderDetailSingleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemNameTxt = binding.itemNameTxt;
            itemDescriptionTxt = binding.itemDescriptionTxt;
            itemPriceTxt = binding.itemPriceTxt;

        }
    }
}
