package htg.haitran.quan_ly_doc_gia;

public class DocGia {
    private String Ma;
    private String Ten;
    private int gioiTinh;
    private boolean check;

    public DocGia(String ma, String ten, int gioiTinh) {
        Ma = ma;
        Ten = ten;
        this.gioiTinh = gioiTinh;
        this.check = false;
    }

    public String getMa() {
        return Ma;
    }

    public void setMa(String ma) {
        Ma = ma;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public boolean isChecked() {
        return check;
    }
    public  void setCheck(boolean check) {
        this.check = check;
    }

}

