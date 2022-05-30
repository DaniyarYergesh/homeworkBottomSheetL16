package ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.homework_recyclerview.R

class Fragment2 : Fragment(R.layout.fragment_2) {

    lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.textView)

        val button1: Button = view.findViewById(R.id.button1)
        button1.setOnClickListener {
            clearAppend(1)
        }

        val button2: Button = view.findViewById(R.id.button2)
        button2.setOnClickListener {
            clearAppend(2)
        }

        val button3: Button = view.findViewById(R.id.button3)
        button3.setOnClickListener {
            clearAppend(3)
        }

        val button4: Button = view.findViewById(R.id.button4)
        button4.setOnClickListener {
            clearAppend(4)
        }

        val button5: Button = view.findViewById(R.id.button5)
        button5.setOnClickListener {
            clearAppend(5)
        }

        val button6: Button = view.findViewById(R.id.button6)
        button6.setOnClickListener {
            clearAppend(6)
        }

        val button7: Button = view.findViewById(R.id.button7)
        button7.setOnClickListener {
            clearAppend(7)
        }

        val button8: Button = view.findViewById(R.id.button8)
        button8.setOnClickListener {
            clearAppend(8)
        }

        val button9: Button = view.findViewById(R.id.button9)
        button9.setOnClickListener {
            clearAppend(9)
        }

        val button_back: Button = view.findViewById(R.id.back)
        button_back.setOnClickListener {
            textView.setTextColor(resources.getColor(R.color.gray_black))
            val asd = textView.getText().toString().dropLast(1)
            textView.setText(asd)
        }
        button_back.setOnLongClickListener {
            textView.setTextColor(resources.getColor(R.color.gray_black))
            textView.text = ""
            true
        }

        val button0: Button = view.findViewById(R.id.button0)
        button0.setOnClickListener {
            clearAppend(0)
        }

        val buttonOk: Button = view.findViewById(R.id.OK)
        val animShake = AnimationUtils.loadAnimation(requireContext(), R.anim.shake_false)
        val animShake2 = AnimationUtils.loadAnimation(requireContext(), R.anim.shake_true)
        buttonOk.setOnClickListener {
            var isSame = false
            if (textView.getText().toString().contains("1567")) {
                isSame = true
            }
            if (isSame == true) {
                textView.setTextColor(resources.getColor(R.color.blue))
                textView.startAnimation(animShake2)
                parentFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.nav_host_fragment_activity_main,
                        Fragment2PersonalPage(), "Fragment"
                    ).addToBackStack(null).commit()
            } else {
                textView.setTextColor(resources.getColor(R.color.red))
                textView.startAnimation(animShake)
            }
        }
    }

    fun clearAppend(number: Int) {
        if (textView.text.contains("Введ")) {
            textView.text = ""
        }
        if (textView.text.length <= 3) {
            textView.append(number.toString())
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("dan", "onSaveInstanceState")
        outState.putString("key", textView.text.toString())
        outState.putInt("key2", textView.currentTextColor)
    }

}