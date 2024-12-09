package com.example.baitaplon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemsanpham;

import java.util.ArrayList;

public class adaptersanpham extends RecyclerView.Adapter<adaptersanpham.ProductViewHolder> {

    private Context context;
    private ArrayList<itemsanpham> products;
    private ArrayList<itemsanpham> cartList = new ArrayList<>();

    public adaptersanpham(Context context, ArrayList<itemsanpham> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteamsanpham, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        itemsanpham product = products.get(position);

        holder.tensp.setText(product.getTen());
        holder.giasp.setText("Giá: " + product.getGia());
        holder.soluongsp.setText("Số lượng: " + product.getSoluong());

        if (product.getAnh() != null) {
            holder.anhsp.setImageURI(Uri.parse(product.getAnh()));
        } else {
            holder.anhsp.setImageResource(R.drawable.burger1);
        }
        holder.btnthemvaogiohang.setOnClickListener(v -> {
            cartList.add(product);
            Toast.makeText(context, product.getTen() + " đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, giohang.class);
            intent.putExtra("cart", cartList);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    public ArrayList<itemsanpham> getCartList() {
        return cartList;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tensp, giasp, soluongsp;
        ImageView anhsp;
        Button btnthemvaogiohang;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.tensp);
            giasp = itemView.findViewById(R.id.giasp);
            soluongsp = itemView.findViewById(R.id.soluongsp);
            anhsp = itemView.findViewById(R.id.anhsp);
            btnthemvaogiohang = itemView.findViewById(R.id.btnthemvaogiohang);
        }
    }
}
