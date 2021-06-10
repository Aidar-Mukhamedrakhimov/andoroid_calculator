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

    private static final String _POINT_SIGN = ".";
    private static final String _PERCENT_SIGN = "%";
    private static final String _DIVIDE_SIGN = "/";
    private static final String _MULTIPLY_SIGN = "*";
    private static final String _MINUS_SIGN = "-";
    private static final String _PLUS_SIGN = "+";


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
        if (!stringEndsOverride(expr,_POINT_SIGN,_DIVIDE_SIGN,_MULTIPLY_SIGN,_MINUS_SIGN,_PLUS_SIGN))
        {
            if (expr.contains(_PERCENT_SIGN))
                expr=expr.replaceAll(_PERCENT_SIGN,"/100");
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
        String strExpr = calculator.getExpr();
        if (str.equals(_PERCENT)) {
            if (!strExpr.isEmpty() &&
                !stringEndsOverride(strExpr,_PERCENT_SIGN,_DIVIDE_SIGN,_MULTIPLY_SIGN,_MINUS_SIGN,_PLUS_SIGN))
            {
                appendExpr(calculator, _PERCENT_SIGN);
                calculator.setPointFlag(false);
            }
        } else if (str.equals(_DIVIDE)) {
            if (!strExpr.isEmpty() &&
            !stringEndsOverride(strExpr,_PERCENT_SIGN,_DIVIDE_SIGN,_MULTIPLY_SIGN,_MINUS_SIGN,_PLUS_SIGN))
            {
                appendExpr(calculator, _DIVIDE_SIGN);
                calculator.setPointFlag(false);
            }
        } else if (str.equals(_MULTIPLY)) {
            if (!strExpr.isEmpty() &&
            !stringEndsOverride(strExpr,_PERCENT_SIGN,_DIVIDE_SIGN,_MULTIPLY_SIGN,_MINUS_SIGN,_PLUS_SIGN))
            {
                appendExpr(calculator, _MULTIPLY_SIGN);
                calculator.setPointFlag(false);
            }
        } else if (str.equals(_MINUS)) {
            if (!stringEndsOverride(strExpr,_PERCENT_SIGN,_DIVIDE_SIGN,_MULTIPLY_SIGN,_MINUS_SIGN,_PLUS_SIGN))
            {
                appendExpr(calculator, _MINUS_SIGN);
                calculator.setPointFlag(false);
            }
        } else if (str.equals(_PLUS)) {
            if (!strExpr.isEmpty() &&
            !stringEndsOverride(strExpr,_PERCENT_SIGN,_DIVIDE_SIGN,_MULTIPLY_SIGN,_MINUS_SIGN,_PLUS_SIGN))
            {
                appendExpr(calculator, _PLUS_SIGN);
                calculator.setPointFlag(false);
            }
        } else appendExpr(calculator,str);
    }

    private static void eraseValue(Calculator calculator){
        String str = calculator.getExpr();
        if (!str.isEmpty()) {
            if (str.endsWith(_POINT_SIGN))
                calculator.setPointFlag(false);
            calculator.setExpr(str.substring(0, str.length() - 1));
        }
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
            calculator.setExpr(calculator.getExpr() + _POINT_SIGN);
            calculator.setPointFlag(true);
        }
    }

    private static boolean stringEndsOverride(String strExpr, String... strs){
        boolean flag = false;
        for (String str:strs){
            if (strExpr.endsWith(str))
                flag=true;
        }
        return flag;
    }
}
