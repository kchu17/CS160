package edu.sjsu.android.cs_160_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList images, titles, prices, shows;
    private List<MenuEntry>menu;

    public HelperAdapter() {
    }

    public HelperAdapter(List<MenuEntry>menu, Context context) {
        this.menu = menu;
        this.context = context;
    }

    public void setMenu(List<MenuEntry>menu) {
        this.menu = menu;
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

        Log.d("MENUENTRY", "onBindViewHolder: data: " + menu);
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
//        viewHolderClass.imageView.setImageResource(MockData.images[position]);

        Glide.with(context).load(menu.get(position).getImage_url()).into(viewHolderClass.imageView);
        viewHolderClass.title.setText(menu.get(position).getName());
        viewHolderClass.price.setText(Double.toString(menu.get(position).getPrice()));
        viewHolderClass.desc.setText(menu.get(position).getDescription());
        viewHolderClass.show.setChecked(menu.get(position).getShow());


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
        return menu.size();
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
