package com.example.baitaplon;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemsanpham;

import java.util.ArrayList;


public class adaptergiohang extends RecyclerView.Adapter<adaptergiohang.CartViewHolder> {
    private Context context;
    private ArrayList<itemsanpham> cartItems;
    private OnCartActionListener cartActionListener;


    public adaptergiohang(Context context, ArrayList<itemsanpham> cartItems, OnCartActionListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartActionListener = listener;
    }

    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemgiohang, parent, false);
        return new CartViewHolder(view);
    }

    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        itemsanpham item = cartItems.get(position);
        holder.tengh.setText(item.getTen());
        holder.giagh.setText("Giá: " + item.getGia() + " VND");
        holder.soluonggh.setText("Số lượng: " + item.getSoluong());

        if (item.getAnh() != null) {
            holder.anhgh.setImageURI(Uri.parse(item.getAnh()));
        }

        holder.btnconggh.setOnClickListener(v -> cartActionListener.onIncreaseQuantity(position));
        holder.btntrugh.setOnClickListener(v -> cartActionListener.onDecreaseQuantity(position));
        holder.btnxoagh.setOnClickListener(v -> cartActionListener.onRemoveItem(position));
    }

    public int getItemCount() {
        return cartItems.size();
    }

    public interface OnCartActionListener {
        void onIncreaseQuantity(int position);

        void onDecreaseQuantity(int position);

        void onRemoveItem(int position);
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tengh, giagh, soluonggh;
        ImageView anhgh;
        ImageButton btnxoagh,btnconggh, btntrugh;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tengh = itemView.findViewById(R.id.tengh);
            giagh = itemView.findViewById(R.id.giagh);
            soluonggh = itemView.findViewById(R.id.soluonggh);
            anhgh = itemView.findViewById(R.id.anhgh);
            btnconggh = itemView.findViewById(R.id.btnconggh);
            btntrugh = itemView.findViewById(R.id.btntrugh);
            btnxoagh = itemView.findViewById(R.id.btnxoagh);
        }
    }
}
