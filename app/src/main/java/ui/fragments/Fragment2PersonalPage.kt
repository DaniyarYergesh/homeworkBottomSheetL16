package ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.homework_recyclerview.R

class Fragment2PersonalPage: Fragment(R.layout.fragment_2_personal_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val camera: TextView = view.findViewById(R.id.string5)
        camera.setOnClickListener { dispatchTakePictureIntent() }


        val call: TextView = view.findViewById(R.id.string4)
        call.setOnClickListener {
            dialPhoneNumber()
        }


        val mail: TextView = view.findViewById(R.id.string3)
        mail.setOnClickListener {composeEmail()}


        val share: TextView = view.findViewById(R.id.string2)
        share.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "*/*"
            startActivity(Intent.createChooser(sharingIntent, "Share using"))
        }
    }

    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    fun dialPhoneNumber() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${87}")
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    fun composeEmail(){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_EMAIL, "")
            putExtra(Intent.EXTRA_SUBJECT, "")
            putExtra(Intent.EXTRA_STREAM, "")
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}