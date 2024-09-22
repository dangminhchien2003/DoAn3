package Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apponthiblx.R;

import java.util.ArrayList;

import DTO.LoaiBienBao;

public class LoaiBienBaoAdapter extends RecyclerView.Adapter<LoaiBienBaoAdapter.LoaiBienBaoViewHolder> {

    private Context context;
    private ArrayList<LoaiBienBao> list;

    public LoaiBienBaoAdapter(Context context,int layout, ArrayList<LoaiBienBao> list) {
        this.context = context;
        this.list = list;
    }


    public interface OnItemClickListener {
        void onItemClick(LoaiBienBao loaiBienBao);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public LoaiBienBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loaibienbao, parent, false);
        return new LoaiBienBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiBienBaoViewHolder holder, int position) {
        LoaiBienBao loaiBienBao = list.get(position);
        holder.tenLoaiBB.setText(loaiBienBao.getTenloaiBB());
        holder.mota.setText(loaiBienBao.getMota());

        // Lấy tài nguyên Drawable từ thư mục drawable
        Drawable drawable = context.getResources().getDrawable(
                context.getResources().getIdentifier(
                        loaiBienBao.getAnh(), "drawable", context.getPackageName()));

        // Gán hình ảnh vào ImageView
        holder.imgLoaiBB.setImageDrawable(drawable);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(loaiBienBao);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LoaiBienBaoViewHolder extends RecyclerView.ViewHolder {

        TextView tenLoaiBB, mota;
        ImageView imgLoaiBB;

        public LoaiBienBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            tenLoaiBB = itemView.findViewById(R.id.textViewTenloaibien);
            mota = itemView.findViewById(R.id.textViewmota);
            imgLoaiBB = itemView.findViewById(R.id.imageViewloaibienbao);
        }
    }
}
