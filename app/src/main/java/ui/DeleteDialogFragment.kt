package ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.homework_recyclerview.R

class DeleteDialogFragment() :DialogFragment(R.layout.custom_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view){
            findViewById<Button>(R.id.del_button).setOnClickListener {
                (activity as? DeleteDialogCallback)?.onDeleteButton()
                dismiss()
            }
            findViewById<Button>(R.id.cancel_button).setOnClickListener {
                dismiss()
            }
        }
    }
}

interface DeleteDialogCallback{
    fun onDeleteButton()
}