package Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apponthiblx.R;

import java.util.ArrayList;

import DTO.BienBao;
import DTO.LoaiBienBao;

public class BienBaoAdapter extends RecyclerView.Adapter<BienBaoAdapter.BienBaoViewHolder>{

    private Context context;
    private ArrayList<BienBao> listbb;

    public BienBaoAdapter(Context context,int layout, ArrayList<BienBao> listbb) {
        this.context = context;
        this.listbb = listbb;
    }

    @NonNull
    @Override
    public BienBaoAdapter.BienBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bienbao, parent, false);
        return new BienBaoAdapter.BienBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BienBaoAdapter.BienBaoViewHolder holder, int position) {
        BienBao bienBao = listbb.get(position);
        holder.sobien.setText(bienBao.getSobien());
        holder.tenBB.setText(bienBao.getTenbien());
        holder.mota.setText(bienBao.getMota());


        // Lấy tài nguyên Drawable từ thư mục drawable
        Drawable drawable = context.getResources().getDrawable(
                context.getResources().getIdentifier(
                        bienBao.getAnh(), "drawable", context.getPackageName()));

        // Gán hình ảnh vào ImageView
        holder.imgBB.setImageDrawable(drawable);

        holder.cardViewbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị hoặc ẩn TextView mô tả khi click vào CardView
                if (holder.mota.getVisibility() == View.VISIBLE) {
                    holder.mota.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out));
                    holder.mota.setVisibility(View.GONE); // Ẩn TextView nếu đang hiển thị
                } else {
                    holder.mota.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in));
                    holder.mota.setVisibility(View.VISIBLE); // Hiển thị TextView nếu đang ẩn
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listbb.size();
    }

    public static class BienBaoViewHolder extends RecyclerView.ViewHolder {

        TextView sobien, tenBB, mota;
        ImageView imgBB;
        CardView cardViewbb;

        public BienBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            sobien = itemView.findViewById(R.id.textViewsobien);
            tenBB = itemView.findViewById(R.id.textViewTenbien);
            mota = itemView.findViewById(R.id.textViewmota);
            imgBB = itemView.findViewById(R.id.imageViewbienbao);
            cardViewbb = itemView.findViewById(R.id.cabviewbb);

        }
    }
}
