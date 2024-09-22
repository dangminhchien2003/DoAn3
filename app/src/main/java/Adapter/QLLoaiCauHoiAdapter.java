package Adapter;

import static androidx.core.app.ActivityCompat.startActivityForResult;

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
import com.example.apponthiblx.SuaLoaiCauhoiActivity;

import java.util.ArrayList;
import java.util.List;

import DAO.LoaiCauHoiDAO;
import DTO.LoaiBienBao;
import DTO.LoaiCauHoi;

public class QLLoaiCauHoiAdapter extends RecyclerView.Adapter<QLLoaiCauHoiAdapter.QLLoaiCauHoiViewHolder> {

    private Context context;
    private ArrayList<LoaiCauHoi> listLCH;
    private LoaiCauHoiDAO loaiCauHoiDAO;
    private static final int REQUEST_CODE_EDIT = 1;

    public QLLoaiCauHoiAdapter(Context context, int item_qlloaicauhoi, ArrayList<LoaiCauHoi> listLCH) {
        this.context = context;
        this.listLCH = listLCH;
        this.loaiCauHoiDAO = new LoaiCauHoiDAO(context);
    }

    @NonNull
    @Override
    public QLLoaiCauHoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qlloaicauhoi, parent, false);
        return new QLLoaiCauHoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLLoaiCauHoiViewHolder holder, int position) {
        LoaiCauHoi loaiCauHoi = listLCH.get(position);
        holder.tenLoai.setText(loaiCauHoi.getTenloai());
        holder.mota.setText(loaiCauHoi.getMota());

        // Lấy ID của tài nguyên Drawable
        int resourceId = context.getResources().getIdentifier(
                loaiCauHoi.getAnh(), "drawable", context.getPackageName());

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
                Intent intent = new Intent(context, SuaLoaiCauhoiActivity.class);
                intent.putExtra("MA_LOAI", loaiCauHoi.getId());
                intent.putExtra("TEN_LOAI", loaiCauHoi.getTenloai());
                intent.putExtra("MO_TA", loaiCauHoi.getMota());
                intent.putExtra("ANH", loaiCauHoi.getAnh());
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLoaiCauHoi(loaiCauHoi.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLCH.size();
    }
    public void setData(List<LoaiCauHoi> loaiCauHoiList) {
        listLCH.clear();
        listLCH.addAll(loaiCauHoiList);
        notifyDataSetChanged();
    }
    public class QLLoaiCauHoiViewHolder extends RecyclerView.ViewHolder {
        TextView tenLoai, mota;
        ImageView imgLoai, imgxoa;

        public QLLoaiCauHoiViewHolder(@NonNull View itemView) {
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
        builder.setMessage("Bạn có muốn xóa loại câu hỏi này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa loại câu hỏi
                boolean isSuccess = loaiCauHoiDAO.delete(id);
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
        ArrayList<LoaiCauHoi> newList = loaiCauHoiDAO.getAll();
        listLCH.clear();
        listLCH.addAll(newList);
        notifyDataSetChanged();
    }

}