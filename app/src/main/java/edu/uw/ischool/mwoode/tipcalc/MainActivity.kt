package edu.uw.ischool.mwoode.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    private var isProgrammaticChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        val tipInput = findViewById<EditText>(R.id.tipAmount);
        val submitTip = findViewById<Button>(R.id.submitTip);

        submitTip.setOnClickListener{
            Log.i("INFO", "Clicked!");
            calculateTip(tipInput.text.toString())
        }

        tipInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString();

                Log.i("INFO", text);
                if (!text.isEmpty()) {
                    val formattedText = formatMoney(text);
                    tipInput.removeTextChangedListener(this);
                    tipInput.setText(formattedText);
                    tipInput.setSelection(formattedText.length);
                    tipInput.addTextChangedListener(this);

                    submitTip.isEnabled = formattedText != "00.00"

                } else {
                    submitTip.isEnabled = false;
                }

            }
        })
    }

    fun calculateTip(inputStr: String) {
        val inputVal = inputStr.replace(Regex("^\\\$"), "").toDouble();
        val tipVal = inputVal * 0.15;

        val tipAmountDisplay = findViewById<TextView>(R.id.tipAmountDisplay);
        tipAmountDisplay.setText("$" + String.format("%.2f", tipVal));

        val tipDisplay = findViewById<LinearLayout>(R.id.tipDisplay);
        tipDisplay.visibility = View.VISIBLE;
    }

    fun formatMoney(inputVal: String):String {
        val formattedText = inputVal
            .replace(Regex("^\\\$0*"), "")
            .replace(Regex("[^0-9]"), "")
            .padStart(4, '0');

        val whole = formattedText.substring(0, formattedText.length - 2);
        val decimal = formattedText.substring(formattedText.length - 2);

        return "$$whole.$decimal"
    }
}