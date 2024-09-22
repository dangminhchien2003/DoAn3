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
import com.example.apponthiblx.SuaBienBaoActivity;
import com.example.apponthiblx.SuaLoaiBienBaoActivity;

import java.util.ArrayList;
import java.util.List;

import DAO.BienBaoDAO;
import DAO.LoaiBienBaoDAO;
import DTO.BienBao;
import DTO.CauHoi;
import DTO.LoaiBienBao;

public class QLBienBaoAdapter extends RecyclerView.Adapter<QLBienBaoAdapter.QLBienBaoViewHolder> {
    private Context context;
    private ArrayList<BienBao> listBB;
    private BienBaoDAO bienBaoDAO;
    private static final int REQUEST_CODE_EDIT = 1;
    public QLBienBaoAdapter(Context context, int item_qlloaicauhoi, ArrayList<BienBao> listBB) {
        this.context = context;
        this.listBB = listBB;
        this.bienBaoDAO = new BienBaoDAO(context);
    }

    @NonNull
    @Override
    public QLBienBaoAdapter.QLBienBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qlbienbao, parent, false);
        return new QLBienBaoAdapter.QLBienBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLBienBaoAdapter.QLBienBaoViewHolder holder, int position) {
        BienBao bienBao = listBB.get(position);
        holder.sobien.setText(bienBao.getSobien());
        holder.ten.setText(bienBao.getTenbien());
        holder.mota.setText(bienBao.getMota());

        // Lấy ID của tài nguyên Drawable
        int resourceId = context.getResources().getIdentifier(
                bienBao.getAnh(), "drawable", context.getPackageName());

        // Kiểm tra nếu resourceId là 0 (tài nguyên không tồn tại), thì sử dụng ảnh mặc định
        Drawable drawable;
        if (resourceId != 0) {
            drawable = context.getResources().getDrawable(resourceId);
        } else {
            // Sử dụng ảnh mặc định nếu tài nguyên không tồn tại
            drawable = context.getResources().getDrawable(R.drawable.adddethi);
        }

        // Gán hình ảnh vào ImageView
        holder.img.setImageDrawable(drawable);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuaBienBaoActivity.class);
                intent.putExtra("MA", bienBao.getId());
                intent.putExtra("MA_LOAI", bienBao.getId_loai());
                intent.putExtra("SO_BIEN", bienBao.getSobien());
                intent.putExtra("TEN", bienBao.getTenbien());
                intent.putExtra("MO_TA", bienBao.getMota());
                intent.putExtra("ANH", bienBao.getAnh());
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(bienBao.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBB.size();
    }
    public void setData(List<BienBao> bienBaoList) {
        listBB.clear();
        listBB.addAll(bienBaoList);
        notifyDataSetChanged();
    }

    public class QLBienBaoViewHolder extends RecyclerView.ViewHolder {
        TextView ten, mota, sobien;
        ImageView img, imgxoa;

        public QLBienBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.textViewTenbien);
            mota = itemView.findViewById(R.id.textViewmota);
            img = itemView.findViewById(R.id.imageViewbienbao);
            imgxoa = itemView.findViewById(R.id.imageViewxoa);
            sobien = itemView.findViewById(R.id.textViewsobien);
        }
    }

    private void delete(String id) {
        // Hiển thị hộp thoại xác nhận xóa
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa loại biển báo này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa loại câu hỏi
                boolean isSuccess = bienBaoDAO.delete(id);
                if (isSuccess) {
                    // Thông báo và cập nhật lại danh sách
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    loadLoaiCauHoiData();
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

    private void loadLoaiCauHoiData() {
        ArrayList<BienBao> newList = bienBaoDAO.getAll();
        listBB.clear();
        listBB.addAll(newList);
        notifyDataSetChanged();
    }

}
