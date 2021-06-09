package kz.gk.calcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txtViewExpr,txtViewHistory;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Button> allBtn = getAllButtons();

        calculator = new Calculator();

        initTextView();
        setButtonListener(allBtn);
        setTxtView();
    }

    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable("Calculator",calculator);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculator = savedInstanceState.getParcelable("Calculator");
        setTxtView();
    }

    /**
     * Посмотрел в интернете, как находить все объекты
     * контейнеров типа LinearLayout, GridLayout и т.д.
     * @var btnContainer - GridLayout, в котором у меня размещены кнопки
     * @return - возвращает коллекцию кнопок
     */
    public List<Button> getAllButtons(){
        GridLayout btnContainer = findViewById(R.id.buttonContainer);
        List<Button> btn = new ArrayList<>();
        for(int i =0; i< btnContainer.getChildCount(); i++){
            View v = btnContainer.getChildAt(i);
            if(v instanceof Button){
                btn.add((Button) v);
            }
        }
        return btn;
    }

    private void initTextView(){
        txtViewHistory = findViewById(R.id.txtViewHistory);
        txtViewExpr = findViewById(R.id.txtViewExpression);
    }

    /**
     * В цикле устанавливаю на каждую кнопку обработчик событий
     * @param btnList - коллекция кнопок, полученная в методе getAllButtons
     */
    private void setButtonListener(List<Button> btnList){
        for (int i=0; i<btnList.size(); i++){
            Button tmpButton = btnList.get(i);
            System.out.println();
            tmpButton.setOnClickListener(v -> {
                calcProcess(tmpButton.getText().toString());
            });
        }
    }

    private void calcProcess(String str){
        CalcOperations.process(calculator,str);
        setTxtView();
    }

    private void setTxtView(){
        txtViewHistory.setText("");
        ArrayList<String> list = calculator.getList();
            for (String str : list) {
                txtViewHistory.setText(str+"\n"+txtViewHistory.getText()+"\n");
            }
        txtViewExpr.setText(calculator.getExpr());
    }




}