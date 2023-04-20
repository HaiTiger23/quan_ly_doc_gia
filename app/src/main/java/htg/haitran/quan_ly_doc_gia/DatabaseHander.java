package htg.haitran.quan_ly_doc_gia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHander extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QLDG";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "DocGia";
    private Context context;
    private static final String KEY_MA = "ma";
    private static final String KEY_TEN = "ten";
    private static final String KEY_GIOI_TINH= "gioitinh";

    public DatabaseHander(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_notes_table = String.format("CREATE TABLE %s(%s TEXT PRIMARY KEY, %s TEXT, %s INTEGER)", TABLE_NAME, KEY_MA, KEY_TEN, KEY_GIOI_TINH);
        db.execSQL(create_notes_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<DocGia> readDB() {
        ArrayList<DocGia> docGiaArrayList = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM DocGia", null);
        if(cursor != null){
            if(cursor.moveToFirst()) {
                do {
                    String ma = cursor.getString(cursor.getColumnIndex("ma"));
                    String ten = cursor.getString(cursor.getColumnIndex("ten"));
                    int gioitinh = cursor.getInt(cursor.getColumnIndex("gioitinh"));
                    docGiaArrayList.add(new DocGia(ma,ten,gioitinh));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }

        return  docGiaArrayList;
    }
    public void insertDB(DocGia docGia) {
        boolean check = true;
        SQLiteDatabase DB = this.getReadableDatabase();
        ArrayList<DocGia> docGiaArrayList = readDB();
        for (int i = 0; i < docGiaArrayList.size(); i++) {
            if(docGiaArrayList.get(i).getMa().equals(docGia.getMa())) {
                check = false;
                break;
            }
        }
        if (check) {
            String sql = "INSERT INTO DocGia(ma,ten,gioitinh) VALUES ('"+docGia.getMa()+"','"+docGia.getTen()+"',"+docGia.getGioiTinh()+")";
            DB.execSQL(sql);
            Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Độc giả đã tồn tại trong danh sách",Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteDB(String ma) {
        SQLiteDatabase DB = this.getWritableDatabase();
        String[] GiaTri = { ma };
        DB.delete(TABLE_NAME,KEY_MA+"= ?",GiaTri);
    }
    public boolean UpdateDB(DocGia docGia) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma", docGia.getMa());
        contentValues.put("ten", docGia.getTen());
        contentValues.put("gioitinh", docGia.getGioiTinh());
        String[] GiaTri = { docGia.getMa() };
        SQLiteDatabase DB = this.getWritableDatabase();
        int count = DB.update(TABLE_NAME,contentValues,KEY_MA+"= ?",GiaTri);
        if (count > 0) {
            return  true;
        }
        return  false;
    }
}
