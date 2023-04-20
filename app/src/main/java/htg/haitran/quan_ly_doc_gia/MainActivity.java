package htg.haitran.quan_ly_doc_gia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase DB;
    DatabaseHander databaseHander = new DatabaseHander(MainActivity.this);
    EditText edtMa,edtTen;
    RadioButton rbNu,rbNam;
    Button btnAdd;
    ImageButton btnDelete;
    ListView lv;
    ArrayList<DocGia> docGiaList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        docGiaList = databaseHander.readDB();;
        CustomAdapter adapter = new CustomAdapter(MainActivity.this,docGiaList);
        lv.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma,ten;
                int gioitinh = -1;
                ma = edtMa.getText().toString();
                ten = edtTen.getText().toString();
                if(rbNam.isChecked()) {
                    gioitinh = 1;
                }else if (rbNu.isChecked()){
                    gioitinh = 0;
                }

                if(ma.length() == 0 || ten.length() == 0 || gioitinh == -1) {
                    Toast.makeText(MainActivity.this,"Bạn cần nhập đủ thông tin",Toast.LENGTH_SHORT).show();
                }else {
                    databaseHander.insertDB(new DocGia(ma,ten,gioitinh));
                    docGiaList  = databaseHander.readDB();
                    CustomAdapter adapter = new CustomAdapter(MainActivity.this,docGiaList);
                    lv.setAdapter(adapter);
                }
            }
        });
    btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa dữ liệu không? ");
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < docGiaList.size(); i++) {
                        if (docGiaList.get(i).isChecked()) {
                            databaseHander.deleteDB(docGiaList.get(i).getMa());
                        }
                    }
                    docGiaList  = databaseHander.readDB();
                    CustomAdapter adapter = new CustomAdapter(MainActivity.this,docGiaList);
                    lv.setAdapter(adapter);
                    Toast.makeText(MainActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    });

    }
    void anhxa() {
        edtMa = findViewById(R.id.edt_ma);
        edtTen = findViewById(R.id.edt_ten);
        rbNu = findViewById(R.id.rb_nu);
        rbNam = findViewById(R.id.rb_nam);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        lv = findViewById(R.id.lv);
    }
    void chuyenTrang(String ma) {

    }


}