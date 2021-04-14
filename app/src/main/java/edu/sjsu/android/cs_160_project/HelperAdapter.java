package edu.sjsu.android.cs_160_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class HelperAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList images, titles, prices, shows;

    public HelperAdapter() {
    }

    public HelperAdapter(Context context, ArrayList images, ArrayList titles, ArrayList prices, ArrayList shows) {
        this.context = context;
        this.images = images;
        this.titles = titles;
        this.prices = prices;
        this.shows= shows;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_item, parent, false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        viewHolderClass.imageView.setImageResource(MockData.images[position]);
        viewHolderClass.title.setText(MockData.titles[position]);
        viewHolderClass.price.setText(MockData.prices[position]);
        viewHolderClass.desc.setText(MockData.descriptions[position]);
        viewHolderClass.show.setChecked(MockData.show[position]);


        // Implement change of the switch function
//        viewHolderClass.show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return MockData.images.length;
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView price;
        TextView desc;
        SwitchCompat show;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            title = (TextView) itemView.findViewById(R.id.item_title);
            price = (TextView) itemView.findViewById(R.id.item_price);
            show = itemView.findViewById(R.id.item_show);
            desc = (TextView) itemView.findViewById(R.id.item_desc);
        }
    }
}
