package com.example.roman

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.roman.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Decimal to Roman converter
        binding.convertToRomanNumeral.setOnClickListener {
            try {
                val input = binding.integerEt.text.toString()
                if (input.isNotEmpty() && input.matches(Regex("\\d+"))) {
                    val number = input.toInt()
                    if (number in 1..3999) {
                        val roman = decimalToRoman(number)
                        binding.romanNumeralTv.text = roman
                    } else {
                        Toast.makeText(this, "Введите число от 1 до 3999", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Введите правильное число", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Возникла ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // Roman to Decimal converter
        binding.convertToDecimal.setOnClickListener {
            try {
                val input = binding.romanEt.text.toString()
                if (input.isNotEmpty() && input.matches(Regex("[IVXLCDM]+"))) {
                    val decimal = romanToDecimal(input)
                    binding.decimalTv.text = decimal.toString()
                } else {
                    Toast.makeText(this, "Введите правильное римское число", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Возникла ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun decimalToRoman(num: Int): String {
        val m = arrayOf("", "M", "MM", "MMM")
        val c = arrayOf("", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM")
        val x = arrayOf("", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC")
        val i = arrayOf("", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX")

        val thousands = m[num / 1000]
        val hundreds = c[num % 1000 / 100]
        val tens = x[num % 100 / 10]
        val ones = i[num % 10]

        return thousands + hundreds + tens + ones
    }

    private fun value(r: Char): Int {
        return when (r) {
            'I' -> 1
            'V' -> 5
            'X' -> 10
            'L' -> 50
            'C' -> 100
            'D' -> 500
            'M' -> 1000
            else -> -1
        }
    }

    private fun romanToDecimal(str: String): Int {
        var res = 0
        var i = 0
        while (i < str.length) {
            val s1 = value(str[i])
            if (i + 1 < str.length) {
                val s2 = value(str[i + 1])
                if (s1 >= s2) {
                    res += s1
                } else {
                    res += s2 - s1
                    i++
                }
            } else {
                res += s1
            }
            i++
        }
        return res
    }
}