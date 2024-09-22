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
import com.example.apponthiblx.SuaLoaiBienBaoActivity;
import com.example.apponthiblx.SuaLoaiCauhoiActivity;

import java.util.ArrayList;
import java.util.List;

import DAO.LoaiBienBaoDAO;
import DTO.CauHoi;
import DTO.LoaiBienBao;

public class QLLoaiBienBaoAdapter extends RecyclerView.Adapter<QLLoaiBienBaoAdapter.QLLoaiBienBaoViewHolder> {
    private Context context;
    private ArrayList<LoaiBienBao> listLBB;
    private LoaiBienBaoDAO loaiBienBaoDAO;
    private static final int REQUEST_CODE_EDIT = 1;
    public QLLoaiBienBaoAdapter(Context context, int item_qlloaicauhoi, ArrayList<LoaiBienBao> listLCH) {
        this.context = context;
        this.listLBB = listLCH;
        this.loaiBienBaoDAO = new LoaiBienBaoDAO(context);
    }

    @NonNull
    @Override
    public QLLoaiBienBaoAdapter.QLLoaiBienBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qlloaicauhoi, parent, false);
        return new QLLoaiBienBaoAdapter.QLLoaiBienBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLLoaiBienBaoAdapter.QLLoaiBienBaoViewHolder holder, int position) {
        LoaiBienBao loaiBienBao = listLBB.get(position);
        holder.tenLoai.setText(loaiBienBao.getTenloaiBB());
        holder.mota.setText(loaiBienBao.getMota());

        // Lấy ID của tài nguyên Drawable
        int resourceId = context.getResources().getIdentifier(
                loaiBienBao.getAnh(), "drawable", context.getPackageName());

        // Kiểm tra nếu resourceId là 0 (tài nguyên không tồn tại), thì sử dụng ảnh mặc định
        Drawable drawable;
        if (resourceId != 0) {
            drawable = context.getResources().getDrawable(resourceId);
        } else {
            // Sử dụng ảnh mặc định nếu tài nguyên không tồn tại
            drawable = context.getResources().getDrawable(R.drawable.adddethi);
        }

        // Gán hình ảnh vào ImageView
        holder.imgLoai.setImageDrawable(drawable);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuaLoaiBienBaoActivity.class);
                intent.putExtra("MA_LOAI", loaiBienBao.getId());
                intent.putExtra("TEN_LOAI", loaiBienBao.getTenloaiBB());
                intent.putExtra("MO_TA", loaiBienBao.getMota());
                intent.putExtra("ANH", loaiBienBao.getAnh());
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLoaiCauHoi(loaiBienBao.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLBB.size();
    }
    public void setData(List<LoaiBienBao> loaiBienBaoList) {
        listLBB.clear();
        listLBB.addAll(loaiBienBaoList);
        notifyDataSetChanged();
    }
    public class QLLoaiBienBaoViewHolder extends RecyclerView.ViewHolder {
        TextView tenLoai, mota;
        ImageView imgLoai, imgxoa;

        public QLLoaiBienBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            tenLoai = itemView.findViewById(R.id.textViewTenQLLoaiCH);
            mota = itemView.findViewById(R.id.textViewQLmota);
            imgLoai = itemView.findViewById(R.id.imageViewQlLoaiCH);
            imgxoa = itemView.findViewById(R.id.imageViewxoa);
        }
    }

    private void deleteLoaiCauHoi(String id) {
        // Hiển thị hộp thoại xác nhận xóa
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa loại biển báo này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa loại câu hỏi
                boolean isSuccess = loaiBienBaoDAO.delete(id);
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
                // Không làm gì nếu không xóa
            }
        });
        builder.show();
    }

    private void loadLoaiCauHoiData() {
        ArrayList<LoaiBienBao> newList = loaiBienBaoDAO.getAll();
        listLBB.clear();
        listLBB.addAll(newList);
        notifyDataSetChanged();
    }

}
