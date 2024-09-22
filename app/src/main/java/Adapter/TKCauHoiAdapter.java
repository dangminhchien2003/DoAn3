package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apponthiblx.R;

import java.util.List;

import DTO.CauHoi;

public class TKCauHoiAdapter extends RecyclerView.Adapter<TKCauHoiAdapter.TKCauHoiViewHolder> {
    private List<CauHoi> CauHoiList;

    public TKCauHoiAdapter(List<CauHoi> CauHoiList) {
        this.CauHoiList = CauHoiList;
    }

    @NonNull
    @Override
    public TKCauHoiAdapter.TKCauHoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timkiem_cauhoi, parent, false);
        return new TKCauHoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TKCauHoiAdapter.TKCauHoiViewHolder holder, int position) {
        CauHoi cauHoi = CauHoiList.get(position);
        holder.textViewCau.setText(cauHoi.getCau());
        holder.textViewCauhoi.setText(cauHoi.getNoidung());
        holder.textViewDapAnA.setText(cauHoi.getDapanA());
        holder.textViewDapAnB.setText(cauHoi.getDapanB());
        holder.textViewDapAnC.setText(cauHoi.getDapanC());
        holder.textViewDapAnD.setText(cauHoi.getDapanD());
        holder.textViewDapAnDung.setText("Đáp án đúng: " + cauHoi.getDapandung());


        if (cauHoi.getAnh() == null || cauHoi.getAnh().isEmpty()) {
            holder.imageViewCauhoi.setVisibility(View.GONE);
        } else {
            holder.imageViewCauhoi.setVisibility(View.VISIBLE);

            Context context = holder.itemView.getContext();
            int imageResId = context.getResources().getIdentifier(cauHoi.getAnh(), "drawable", context.getPackageName());
            holder.imageViewCauhoi.setImageResource(imageResId);
        }
    }

    @Override
    public int getItemCount() {
        return CauHoiList.size();
    }
    public void setData(List<CauHoi> cauHoiList) {
        CauHoiList.clear();
        CauHoiList.addAll(cauHoiList);
        notifyDataSetChanged();
    }
    public class TKCauHoiViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCauhoi,textViewDapAnA,textViewDapAnB,textViewDapAnC,textViewDapAnD,textViewDapAnDung,textViewCau;
        ImageView imageViewCauhoi;

        public TKCauHoiViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCauhoi = itemView.findViewById(R.id.textViewNoiDung);
            imageViewCauhoi = itemView.findViewById(R.id.imageViewCH);
            textViewDapAnA = itemView.findViewById(R.id.textViewDapAnA);
            textViewDapAnB = itemView.findViewById(R.id.textViewDapAnB);
            textViewDapAnC = itemView.findViewById(R.id.textViewDapAnC);
            textViewDapAnD = itemView.findViewById(R.id.textViewDapAnD);
            textViewDapAnDung = itemView.findViewById(R.id.textViewDapAnDung);
            textViewCau = itemView.findViewById(R.id.textViewCau);

        }
    }
}
