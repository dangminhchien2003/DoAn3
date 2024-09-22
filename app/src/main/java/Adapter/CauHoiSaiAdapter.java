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

import java.util.ArrayList;

import DTO.CauHoi;

public class CauHoiSaiAdapter extends RecyclerView.Adapter<CauHoiSaiAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CauHoi> wrongQuestions;

    public CauHoiSaiAdapter(Context context, ArrayList<CauHoi> wrongQuestions) {
        this.context = context;
        this.wrongQuestions = wrongQuestions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cauhoisai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CauHoi cauHoi = wrongQuestions.get(position);
        holder.textViewNoiDung.setText(cauHoi.getNoidung());
        holder.textViewDapAnA.setText( cauHoi.getDapanA());
        holder.textViewDapAnB.setText(cauHoi.getDapanB());
        holder.textViewDapAnC.setText(cauHoi.getDapanC());
        holder.textViewDapAnD.setText(cauHoi.getDapanD());
        holder.textViewDapAnDung.setText("Đáp án đúng: " + cauHoi.getDapandung());

        if (cauHoi.getAnh() == null || cauHoi.getAnh().isEmpty()) {
            holder.imageViewCHSai.setVisibility(View.GONE);
        } else {
            holder.imageViewCHSai.setVisibility(View.VISIBLE);

            Context context = holder.itemView.getContext();
            int imageResId = context.getResources().getIdentifier(cauHoi.getAnh(), "drawable", context.getPackageName());
            holder.imageViewCHSai.setImageResource(imageResId);
        }
    }

    @Override
    public int getItemCount() {
        return wrongQuestions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNoiDung, textViewDapAnA, textViewDapAnB, textViewDapAnC, textViewDapAnD, textViewDapAnDung;
        public ImageView imageViewCHSai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNoiDung = itemView.findViewById(R.id.textViewNoiDung);
            textViewDapAnA = itemView.findViewById(R.id.textViewDapAnA);
            textViewDapAnB = itemView.findViewById(R.id.textViewDapAnB);
            textViewDapAnC = itemView.findViewById(R.id.textViewDapAnC);
            textViewDapAnD = itemView.findViewById(R.id.textViewDapAnD);
            textViewDapAnDung = itemView.findViewById(R.id.textViewDapAnDung);
            imageViewCHSai = itemView.findViewById(R.id.imageViewCHSai);
        }
    }
}
