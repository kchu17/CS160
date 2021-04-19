package edu.sjsu.android.cs_160_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sjsu.android.cs_160_project.databinding.FindRestaurantBinding;

public class FindRestaurantsAdapter extends RecyclerView.Adapter<FindRestaurantsAdapter.ViewHolder> {
    private ArrayList<Restaurant> restaurants;
    private OnRowListener mOnRowListener;

    public FindRestaurantsAdapter(ArrayList<Restaurant> restaurants_input, OnRowListener onRowListener)
    {
        restaurants = restaurants_input;
        mOnRowListener = onRowListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FindRestaurantBinding row = FindRestaurantBinding.inflate(inflater);

        return new ViewHolder(row, mOnRowListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String name = restaurants.get(position).getRestaurantName();
        final String type = restaurants.get(position).getType();
        final double rating = restaurants.get(position).getRating();


        holder.binding.restaurantName.setText(name);
        holder.binding.restaurantType.setText(type);
        holder.binding.restaurantRating.setText(Double.toString(rating));
        holder.binding.restaurantImage.setImageResource(restaurants.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        protected final FindRestaurantBinding binding;
        OnRowListener onRowListener;
        public ViewHolder(FindRestaurantBinding binding, OnRowListener onRowListener)
        {
            super(binding.getRoot());
            this.binding = binding;
            this.onRowListener = onRowListener;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRowListener.onRowClick(getAdapterPosition());
        }
    }


    public interface OnRowListener {
        void onRowClick(int position);
    }
}
