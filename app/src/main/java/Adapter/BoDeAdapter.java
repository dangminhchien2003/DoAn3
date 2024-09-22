package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apponthiblx.R;

import java.util.ArrayList;
import java.util.List;

import DTO.BoDe;
import DTO.LoaiBienBao;

public class BoDeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BoDe> boDeList;

    public BoDeAdapter(Context context, ArrayList<BoDe> boDeList) {
        this.context = context;
        this.boDeList = boDeList;
    }

    @Override
    public int getCount() {
        return boDeList.size();
    }

    @Override
    public Object getItem(int position) {
        return boDeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bode, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.TextViewDe);
        textView.setText(boDeList.get(position).getTen_de());

        return convertView;
    }
}

