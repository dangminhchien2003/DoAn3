package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apponthiblx.R;
import com.example.apponthiblx.SuaBoDeActivity;
import com.example.apponthiblx.SuaLoaiBienBaoActivity;

import java.util.ArrayList;
import java.util.List;

import DAO.BoDeDAO;
import DTO.BoDe;
import DTO.LoaiCauHoi;

public class QLBoDeAdapter extends RecyclerView.Adapter<QLBoDeAdapter.QLBoDeViewHolder>{
    private Context context;
    private ArrayList<BoDe> listLBD;
    private BoDeDAO boDeDAO;
    private static final int REQUEST_CODE_EDIT = 1;
    public QLBoDeAdapter(Context context, int item_qlbode, ArrayList<BoDe> listBD) {
        this.context = context;
        this.listLBD = listBD;
        this.boDeDAO = new BoDeDAO(context);
    }
    @NonNull
    @Override
    public QLBoDeAdapter.QLBoDeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qlbode, parent, false);
        return new QLBoDeAdapter.QLBoDeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLBoDeAdapter.QLBoDeViewHolder holder, int position) {
        BoDe boDe = listLBD.get(position);
        holder.tenDe.setText(boDe.getTen_de());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuaBoDeActivity.class);
                intent.putExtra("MA", boDe.getId_de());
                intent.putExtra("TEN", boDe.getTen_de());
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(boDe.getId_de());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLBD.size();
    }
    public void setData(List<BoDe> boDeList) {
        listLBD.clear();
        listLBD.addAll(boDeList);
        notifyDataSetChanged();
    }
    public class QLBoDeViewHolder extends RecyclerView.ViewHolder {
        TextView tenDe;
        ImageView imgxoa;
        public QLBoDeViewHolder(@NonNull View itemView) {
            super(itemView);
                tenDe = itemView.findViewById(R.id.textViewTende);
                imgxoa = itemView.findViewById(R.id.imageViewxoa);
            }
        }
    private void delete(String id) {
        // Hiển thị hộp thoại xác nhận xóa
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa bộ đề này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa loại câu hỏi
                boolean isSuccess = boDeDAO.delete(id);
                if (isSuccess) {
                    // Thông báo và cập nhật lại danh sách
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    loadBode();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void loadBode() {
        ArrayList<BoDe> newList = boDeDAO.getAll();
        listLBD.clear();
        listLBD.addAll(newList);
        notifyDataSetChanged();
    }
}
