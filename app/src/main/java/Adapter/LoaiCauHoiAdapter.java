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
import DTO.LoaiCauHoi;

public class LoaiCauHoiAdapter extends RecyclerView.Adapter<LoaiCauHoiAdapter.LoaiCauHoiViewHolder>{

    private Context context;
    private ArrayList<LoaiCauHoi> listLCH;

    public LoaiCauHoiAdapter(Context context,int layout, ArrayList<LoaiCauHoi> listLCH) {
        this.context = context;
        this.listLCH = listLCH;
    }


    public interface OnItemClickListener {
        void onItemClick(LoaiCauHoi loaiCauhoi);
    }

    private LoaiCauHoiAdapter.OnItemClickListener mListener;

    public void setOnItemClickListener(LoaiCauHoiAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public LoaiCauHoiAdapter.LoaiCauHoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loaicauhoi, parent, false);
        return new LoaiCauHoiAdapter.LoaiCauHoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiCauHoiAdapter.LoaiCauHoiViewHolder holder, int position) {
        LoaiCauHoi loaiCauHoi = listLCH.get(position);
        holder.tenLoai.setText(loaiCauHoi.getTenloai());
        holder.mota.setText(loaiCauHoi.getMota());

        // Lấy tài nguyên Drawable từ thư mục drawable
        Drawable drawable = context.getResources().getDrawable(
                context.getResources().getIdentifier(
                        loaiCauHoi.getAnh(), "drawable", context.getPackageName()));

        // Gán hình ảnh vào ImageView
        holder.imgLoai.setImageDrawable(drawable);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(loaiCauHoi);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLCH.size();
    }

    public class LoaiCauHoiViewHolder extends RecyclerView.ViewHolder {
        TextView tenLoai, mota;
        ImageView imgLoai;
        public LoaiCauHoiViewHolder(@NonNull View itemView) {
            super(itemView);
            tenLoai = itemView.findViewById(R.id.textViewTenloaibien);
            mota = itemView.findViewById(R.id.textViewmota);
            imgLoai = itemView.findViewById(R.id.imageViewloaibienbao);
        }
    }
}
