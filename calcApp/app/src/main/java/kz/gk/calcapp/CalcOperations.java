package kz.gk.calcapp;

import android.renderscript.Script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalcOperations {

    private static final String _POINT = "ߴ";
    private static final String _CLEAR = "C";
    private static final String _PERCENT = "%";
    private static final String _DIVIDE = "÷";
    private static final String _MULTIPLY = "x";
    private static final String _MINUS = "-";
    private static final String _PLUS = "+";
    private static final String _EQUALS = "=";


    public static void process(Calculator calculator, String str){
        if (calculator.isClearExpr())
            clearCalculator(calculator);
        if (str.equals(_POINT)){
            putPoint(calculator);
        }else if (str.equals(_CLEAR)){
            clearCalculator(calculator);
        }else if (str.isEmpty()){
            eraseValue(calculator);
        }else if (str.equals(_EQUALS)){
            makeResult(calculator);
        }else {
            setCalcOperation(calculator,str);
        }
    }

    private static void makeResult(Calculator calculator){
        String expr = calculator.getExpr();
        if (!expr.endsWith(".")&&!expr.endsWith("/")&&!expr.endsWith("*")
                &&!expr.endsWith("-")&&!expr.endsWith("+"))
        {
            if (expr.contains("%"))
                expr=expr.replaceAll("%","/100");
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
            Double result = null;

            try {
                result = (double) engine.eval(expr);
            } catch (ScriptException e) {
                e.printStackTrace();
            }

            calculator.setResult("=" + String.valueOf(result.doubleValue()));
            calculator.addToList(calculator.getExpr() + calculator.getResult());
            calculator.setClearExpr(true);
        }

    }

    private static void setCalcOperation(Calculator calculator, String str){
            if (str.equals(_PERCENT)) {
                appendExpr(calculator,"%");
                calculator.setPointFlag(false);
            } else if (str.equals(_DIVIDE)) {
                appendExpr(calculator,"/");
                calculator.setPointFlag(false);
            } else if (str.equals(_MULTIPLY)) {
                appendExpr(calculator,"*");
                calculator.setPointFlag(false);
            } else if (str.equals(_MINUS)) {
                appendExpr(calculator,"-");
                calculator.setPointFlag(false);
            } else if (str.equals(_PLUS)) {
                appendExpr(calculator,"+");
                calculator.setPointFlag(false);
            } else appendExpr(calculator,str);
    }

    private static void eraseValue(Calculator calculator){
        String str = calculator.getExpr();
        if (str.endsWith("."))
            calculator.setPointFlag(false);
        calculator.setExpr(str.substring(0,str.length()-1));
    }

    private static void clearCalculator(Calculator calculator){
        calculator.setExpr("");
        calculator.setPointFlag(false);
        calculator.setResult("");
        calculator.setClearExpr(false);
    }

    private static void appendExpr(Calculator calculator, String str){
        calculator.setExpr(calculator.getExpr()+str);
    }

    private static void putPoint(Calculator calculator){
        if (!calculator.getExpr().isEmpty() && !calculator.isPointFlag()) {
            calculator.setExpr(calculator.getExpr() + ".");
            calculator.setPointFlag(true);
        }
    }
}
