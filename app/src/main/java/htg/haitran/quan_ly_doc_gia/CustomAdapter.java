package htg.haitran.quan_ly_doc_gia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private SQLiteDatabase DB;
    Context context;
    ArrayList<DocGia> docGiaList;
    public CustomAdapter(Context context, ArrayList<DocGia> docGiaList) {
        this.context = context;
        this.docGiaList = docGiaList;
    }

    @Override
    public int getCount() {
        return docGiaList.size();
    }

    @Override
    public Object getItem(int position) {
        return docGiaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.item_list_view,parent,false);

        TextView txtText = (TextView) convertView.findViewById(R.id.text);
        txtText.setText(docGiaList.get(position).getMa()+"-"+docGiaList.get(position).getTen());

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        if (docGiaList.get(position).getGioiTinh() == 0) {
            image.setImageResource(R.drawable.ic_baseline_girl_24);
        }else {
            image.setImageResource(R.drawable.ic_baseline_boy_24);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, parent);
                popupMenu.getMenuInflater().inflate(R.menu.listview_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_delete) {
                            Toast.makeText(context,"Bạn chọn xóa",Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.cb);
        checkBox.setChecked(docGiaList.get(position).isChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(docGiaList.get(position).isChecked()) {
                   docGiaList.get(position).setCheck(false);
               }else {
                   docGiaList.get(position).setCheck(true);
               }
            }
        });
        LinearLayout item = (LinearLayout) convertView.findViewById(R.id.item);
        txtText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = docGiaList.get(position).getMa();
                Bundle bundle = new Bundle();
                bundle.putString("ma",ma);
                Intent intent = new Intent(context, Activity2.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }



}

