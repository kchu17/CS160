package edu.sjsu.android.cs_160_project;

import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.sjsu.android.cs_160_project.databinding.MenuSingleItemBinding;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>
{
    private ArrayList<MenuEntry> data;
    private onCartMenuListener mOnCartMenuListener;

    public MenuAdapter(ArrayList<MenuEntry> input_data, onCartMenuListener inputOnCartListener)
    {
        data = input_data;
        mOnCartMenuListener = inputOnCartListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MenuSingleItemBinding row = MenuSingleItemBinding.inflate(inflater);

        return new ViewHolder(row, mOnCartMenuListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuEntry entry = data.get(position);

        holder.binding.menuEntryDescription.setText( entry.getDescription());
        holder.binding.menuEntryName.setText(entry.getName());
        holder.binding.menuPriceTxt.setText("$" + entry.getPrice());

        Glide.with(holder.itemView.getContext()).load(Uri.parse(entry.getImage_url())).into(holder.binding.menuEntryImage);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        protected final MenuSingleItemBinding binding;
        protected final onCartMenuListener onCartMenuListener;
        private final Button addToCart;

        public ViewHolder(MenuSingleItemBinding binding, onCartMenuListener onCartMenuListener) {
            super(binding.getRoot());
            this.binding = binding;
            addToCart = binding.getRoot().findViewById(R.id.addToCartButton);

            this.onCartMenuListener = onCartMenuListener;

            addToCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCartMenuListener.onCartMenuClick(getAdapterPosition());
        }


    }

    public interface onCartMenuListener{
        void onCartMenuClick(int position);
    }
}
