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
import com.example.apponthiblx.SuaCauHoiActivity;

import java.util.ArrayList;
import java.util.List;

import DAO.BienBaoDAO;
import DAO.CauHoiDAO;
import DTO.BienBao;
import DTO.CauHoi;

public class QLCauHoiAdapter extends RecyclerView.Adapter<QLCauHoiAdapter.QLCauHoiViewHolder>{

    private Context context;
    private ArrayList<CauHoi> listCH;
    private CauHoiDAO cauHoiDAO;
    private static final int REQUEST_CODE_EDIT = 1;
    public QLCauHoiAdapter(Context context, int item_qlcauhoi, ArrayList<CauHoi> listCH) {
        this.context = context;
        this.listCH = listCH;
        this.cauHoiDAO = new CauHoiDAO(context);
    }
    @NonNull
    @Override
    public QLCauHoiAdapter.QLCauHoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qlcauhoi, parent, false);
        return new QLCauHoiAdapter.QLCauHoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLCauHoiAdapter.QLCauHoiViewHolder holder, int position) {
        CauHoi cauHoi = listCH.get(position);
        holder.cau.setText(cauHoi.getCau());
        holder.noidung.setText(cauHoi.getNoidung());
        holder.dapana.setText(cauHoi.getDapanA());
        holder.dapanb.setText(cauHoi.getDapanB());
        holder.dapanc.setText(cauHoi.getDapanC());
        holder.dapand.setText(cauHoi.getDapanD());
        holder.dapandung.setText("Đáp án đúng:" + cauHoi.getDapandung());


        // Lấy ID của tài nguyên Drawable
        int resourceId = context.getResources().getIdentifier(
                cauHoi.getAnh(), "drawable", context.getPackageName());

        // Kiểm tra nếu resourceId là 0 (tài nguyên không tồn tại), thì sử dụng ảnh mặc định
        Drawable drawable;
        if (resourceId != 0) {
            drawable = context.getResources().getDrawable(resourceId);
            holder.img.setVisibility(View.VISIBLE);
        } else {
            // Sử dụng ảnh mặc định nếu tài nguyên không tồn tại
            drawable = context.getResources().getDrawable(R.drawable.adddethi);
            holder.img.setVisibility(View.GONE);
        }

        // Gán hình ảnh vào ImageView
        holder.img.setImageDrawable(drawable);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuaCauHoiActivity.class);
                intent.putExtra("MA", cauHoi.getId());
                intent.putExtra("MA_LOAI", cauHoi.getId_loaiCH());
                intent.putExtra("CAU", cauHoi.getCau());
                intent.putExtra("NOI_DUNG", cauHoi.getNoidung());
                intent.putExtra("DAP_AN_A", cauHoi.getDapanA());
                intent.putExtra("DAP_AN_B", cauHoi.getDapanB());
                intent.putExtra("DAP_AN_C", cauHoi.getDapanC());
                intent.putExtra("DAP_AN_D", cauHoi.getDapanD());
                intent.putExtra("DAP_AN_DUNG", cauHoi.getDapandung());
                intent.putExtra("GIAI_THICH", cauHoi.getGiaithich());
                intent.putExtra("ID_DE", cauHoi.getId_de());
                intent.putExtra("ANH", cauHoi.getAnh());
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(cauHoi.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCH.size();
    }
    public void setData(List<CauHoi> cauHoiList) {
        listCH.clear();
        listCH.addAll(cauHoiList);
        notifyDataSetChanged();
    }
    public class QLCauHoiViewHolder extends RecyclerView.ViewHolder {
        TextView cau, noidung, dapana,dapanb,dapanc,dapand,dapandung;
        ImageView img, imgxoa;
        public QLCauHoiViewHolder(@NonNull View itemView) {
            super(itemView);
            cau = itemView.findViewById(R.id.textViewCau);
            noidung = itemView.findViewById(R.id.textViewNoiDung);
            dapana = itemView.findViewById(R.id.textViewDapAnA);
            dapanb = itemView.findViewById(R.id.textViewDapAnB);
            dapanc = itemView.findViewById(R.id.textViewDapAnC);
            dapand = itemView.findViewById(R.id.textViewDapAnD);
            dapandung = itemView.findViewById(R.id.textViewDapAnDung);
            img = itemView.findViewById(R.id.imageViewCH);
            imgxoa = itemView.findViewById(R.id.delete);
        }
    }
    private void delete(String id) {
        // Hiển thị hộp thoại xác nhận xóa
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa câu hỏi này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa loại câu hỏi
                boolean isSuccess = cauHoiDAO.delete(id);
                if (isSuccess) {
                    // Thông báo và cập nhật lại danh sách
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    loadCauHoiData();
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

    private void loadCauHoiData() {
        ArrayList<CauHoi> newList = cauHoiDAO.getAllCauHoi();
        listCH.clear();
        listCH.addAll(newList);
        notifyDataSetChanged();
    }
}
