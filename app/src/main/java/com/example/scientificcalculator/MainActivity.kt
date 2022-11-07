package com.example.scientificcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import org.mariuszgromada.math.mxparser.Expression
import java.lang.Double.isNaN
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var previousCalculation: TextView
    private lateinit var display: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        previousCalculation = findViewById(R.id.previousCalculationView)
        display = findViewById(R.id.displayEditText)

        display.setShowSoftInputOnFocus(false)

    }

    fun zeroBTN(view: View) {
        if(display.text.length == 1 && display.text[0].equals('0')) {
            updateText("")
            Toast.makeText(applicationContext, "You cannot enter second zero", Toast.LENGTH_SHORT).show()
        }
        else {
            updateText("0")
        }
    }

    fun oneBTN(view: View) {
        updateText("1")
    }

    fun twoBTN(view: View) {
        updateText("2")
    }

    fun threeBTN(view: View) {
        updateText("3")
    }

    fun fourBTN(view: View) {
        updateText("4")
    }

    fun fiveBTN(view: View) {
        updateText("5")
    }

    fun sixBTN(view: View) {
        updateText("6")
    }

    fun sevenBTN(view: View) {
        updateText("7")
    }

    fun eightBTN(view: View) {
        updateText("8")
    }

    fun nineBTN(view: View) {
        updateText("9")
    }

    private fun forbidActions(mEditText: TextView){
        mEditText.customSelectionActionModeCallback = object : ActionMode.Callback{
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                return true
            }

            override fun onDestroyActionMode(p0: ActionMode?) {}
        }
    }

    private fun updateText(strToAdd:String) {
        var oldStr:String = display.getText().toString()

        var cursorPosition:Int = display.getSelectionStart()
        var leftStr:String = oldStr.substring(0, cursorPosition)
        var rightStr:String = oldStr.substring(cursorPosition)

        forbidActions(display)
        display.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr))
        display.setSelection(cursorPosition + strToAdd.length)

    }

    fun clearBTN(view: View) {
        display.setText("")
        previousCalculation.setText("")
    }

    fun backSpaceBTN(view: View) {
        var cursorPosition = display.getSelectionStart()
        var textLength = display.getText().length

        if(cursorPosition != 0 && textLength != 0) {
            var selection: SpannableStringBuilder = display.getText() as SpannableStringBuilder
            selection.replace(cursorPosition - 1, cursorPosition, "")
            display.setText(selection)
            display.setSelection(cursorPosition - 1)
        }
    }

    fun proBTN(view: View) {
        var constraint: ConstraintLayout = findViewById(R.id.pro)
        constraint.visibility = View.VISIBLE
    }

    fun parenthesesBTN(view: View) {
        var openPar:Int = 0
        var closedPar:Int = 0
        var cursorPosition:Int = display.getSelectionStart()
        var textLength:Int = display.getText().length

        var i:Int = 0
        while(i < cursorPosition) {
            if(display.getText().toString().substring(i, i+1).equals("(")) {
                openPar += 1
            }
            if(display.getText().toString().substring(i, i+1).equals(")")) {
                closedPar += 1
            }
            i++
        }

        if(openPar == closedPar || display.getText().toString().
            substring(textLength - 1, textLength).equals("(")) {
            updateText("(")
        }
        else if(closedPar < openPar && !display.getText().toString().
            substring(textLength - 1, textLength).equals("(")) {
            updateText(")")
        }
        display.setSelection(cursorPosition + 1)

    }

    fun divideBTN(view: View) {
        updateText("/")
    }

    fun multiplyBTN(view: View) {
        updateText("*")
    }

    fun subtractBTN(view: View) {
        updateText("-")
    }

    fun addBTN(view: View) {
        updateText("+")
    }

    fun decimalBTN(view: View) {
        var canAddDecimalEnd = false
        for(i in display.text) {
            if(i == '.') {
                canAddDecimalEnd = true
                Toast.makeText(applicationContext, "You already have point in number", Toast.LENGTH_SHORT).show()
            }
        }
        if(display.text.isEmpty()) {
            updateText("0.")
        }
        else if(!canAddDecimalEnd) {
            updateText(".")
        }
//        var canAddDecimalStart = false
//        var canAddDecimalEnd = false
//        var canAddDecimal = false
//        for(i in display.text){
//            if(i == '*' || i == '/' || i == '+' || i == '-'){
//                val position = display.text.indexOf(i)
//                var startText = display.text.subSequence(0, position - 1).toString()
//                var endText = display.text.subSequence(position, display.text.length).toString()
//
//                for(i in startText) {
//                    if(i == '.') {
//                        canAddDecimalStart = true
//                    }
//                }
//                if(startText.isEmpty()) {
//                    updateText("0.")
//                }
//                else if(!canAddDecimalStart) {
//                    updateText(".")
//                }
//
//                for(i in endText) {
//                    if(i == '.') {
//                        canAddDecimalEnd = true
//                    }
//                }
//                if(endText.isEmpty()) {
//                    updateText("0.")
//                }
//                else if(!canAddDecimalEnd) {
//                    updateText(".")
//                }
//            }
//            else{
//                for(i in display.text) {
//                    if(i == '.') {
//                        canAddDecimal = true
//                    }
//                }
//                if(display.text.isEmpty()) {
//                    updateText("0.")
//                }
//                else if(!canAddDecimal) {
//                    updateText(".")
//                }
//            }
//        }
    }

    fun equalsBTN(view: View) {
        var userExp:String = display.getText().toString()

        previousCalculation.setText(userExp)

        var counter:Int = 0

        try{

            for(i in userExp){
                if(i=='('){
                    counter++
                }
                if(i==')'){
                    counter--
                }
            }

            while(counter != 0){
                if(counter>0){
                    display.getText().insert(userExp.length,")")
                    userExp = display.getText().toString()
                    previousCalculation.setText(userExp)
                    counter --
                }
                if(counter<0){
                    userExp = display.text.subSequence(0, userExp.length - 1).toString()
                    previousCalculation.setText(userExp)
                    counter ++
                }
            }

            var exp: Expression = Expression(userExp)

            var result = exp.calculate()

            if(result.toString() == "Infinity"){
                previousCalculation.setText("Infinity")
                display.setText("")
                Toast.makeText(applicationContext, "You received infinity! Please enter correct data", Toast.LENGTH_SHORT).show()
            }

            if(isNaN(result)) {
                previousCalculation.setText("NaN")
                display.setText("")
                Toast.makeText(applicationContext, "You received NaN! Please enter correct data", Toast.LENGTH_SHORT).show()
            }

            var res:BigDecimal = result.toBigDecimal()

            var s:String = res.toString()

            if(s.contains("1.0E")){
                previousCalculation.setText("0")
                s = ""
                Toast.makeText(applicationContext, "Dividing a large number by a small one", Toast.LENGTH_SHORT).show()
            }

            if(s == "0.0"){
                previousCalculation.setText("0")
                s = ""
                Toast.makeText(applicationContext, "Dividing a small number by a large one", Toast.LENGTH_SHORT).show()
            }

            display.setText(s)
            display.setSelection(s.length)

        }
        catch(e: NumberFormatException){
        }

    }

    fun trigSinBTN (view:View) {
        updateText("sin(")
    }

    fun trigCosBTN (view:View) {
        updateText("cos(")
    }

    fun trigTanBTN (view:View) {
        updateText("tan(")
    }

    fun trigArcSinBTN (view:View) {
        updateText("arcsin(")
    }

    fun trigArcCosBTN (view:View) {
        updateText("arccos(")
    }

    fun trigArcTanBTN (view:View) {
        updateText("arctan(")
    }

    fun naturalLogBTN (view:View) {
        updateText("!")
    }

    fun logBTN (view:View) {
        updateText("log(")
    }

    fun squareBTN (view:View) {
        updateText("sqrt(")
    }

    fun absoluteValueBTN (view:View) {
        updateText("abs(")
    }

    fun piBTN (view:View) {
        updateText("pi")
    }

    fun eBTN (view:View) {
        updateText("e")
    }

    fun xSquaredBTN (view:View) {
        updateText("^(2)")
    }

    fun xPowerYBTN (view:View) {
        updateText("^(")
    }

    fun isPrimeBTN (view:View) {
        updateText("ispr(")
    }
}

