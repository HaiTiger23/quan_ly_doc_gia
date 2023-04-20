package htg.haitran.quan_ly_doc_gia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {
DatabaseHander databaseHander = new DatabaseHander(Activity2.this);
ArrayList<DocGia> docGiaArrayList;
EditText edtMa, edtTen;
RadioButton rbNu, rbNam;
Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        anhxa();
        Bundle bundle = getIntent().getExtras();
        String ma = "";
        if (bundle != null) {
             ma = bundle.getString("ma");
        }
        DocGia docGia = null;
        docGiaArrayList = databaseHander.readDB();
        for (int i = 0; i < docGiaArrayList.size(); i++) {
            if (docGiaArrayList.get(i).getMa().equals(ma)) {
                docGia = docGiaArrayList.get(i);
            }
        }
        if(docGia != null) {
            edtMa.setText(docGia.getMa());
            edtTen.setText(docGia.getTen());
            if (docGia.getGioiTinh() == 1) {
                rbNam.setChecked(true);
            }else {
                rbNu.setChecked(true);
            }
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = edtMa.getText().toString();
                String ten = edtTen.getText().toString();
                int gioitinh = -1;
                if(rbNam.isChecked()){
                    gioitinh = 1;
                }else if(rbNu.isChecked()) {
                    gioitinh = 0;
                }
                if(ma.length() == 0 || ten.length() == 0 || gioitinh == -1){
                    Toast.makeText(Activity2.this, "Bạn cần điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    boolean result = databaseHander.UpdateDB(new DocGia(ma,ten,gioitinh));
                    if (result) {
                        Intent intent = new Intent(Activity2.this, MainActivity.class);
                        Toast.makeText(Activity2.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }else {
                        Toast.makeText(Activity2.this,"Cập nhật không thành công, thử lại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    void anhxa() {
        edtMa = findViewById(R.id.edt_ma);
        edtTen = findViewById(R.id.edt_ten);
        rbNu = findViewById(R.id.rb_nu);
        rbNam = findViewById(R.id.rb_nam);
        btnUpdate = findViewById(R.id.btn_update);
    }
}