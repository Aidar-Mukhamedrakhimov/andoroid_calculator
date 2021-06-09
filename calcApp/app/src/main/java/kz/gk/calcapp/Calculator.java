package kz.gk.calcapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Calculator implements Parcelable{
    private String expr,result;
    private ArrayList<String> list;
    private boolean pointFlag, clearExpr;

    public Calculator() {
        this.expr = "";
        this.list = new ArrayList<>();
        this.pointFlag = false;
        this.result = "";
    }

    protected Calculator(Parcel in) {
        expr = in.readString();
        result = in.readString();
        list = in.createStringArrayList();
        pointFlag = in.readByte() != 0;
        clearExpr = in.readByte() != 0;
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void addToList(String expr) {
        this.list.add(expr);
    }

    public boolean isPointFlag() {
        return pointFlag;
    }

    public void setPointFlag(boolean pointFlag) {
        this.pointFlag = pointFlag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isClearExpr() {
        return clearExpr;
    }

    public void setClearExpr(boolean clearExpr) {
        this.clearExpr = clearExpr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expr);
        dest.writeString(result);
        dest.writeStringList(list);
        dest.writeByte((byte) (pointFlag ? 1 : 0));
        dest.writeByte((byte) (clearExpr ? 1 : 0));
    }
}
