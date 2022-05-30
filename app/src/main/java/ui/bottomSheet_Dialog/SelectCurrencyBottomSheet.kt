package ui.bottomSheet_Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.homework_recyclerview.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectCurrencyBottomSheet: BottomSheetDialogFragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.select_flag_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val euroFlag = view.findViewById<TextView>(R.id.euroFlag)
        val americaFlag = view.findViewById<TextView>(R.id.americaFlag)
        val turkeyFlag = view.findViewById<TextView>(R.id.turkeyFlag)
        val russiaFlag = view.findViewById<TextView>(R.id.russiaFlag)


        euroFlag.setOnClickListener {
            (parentFragment as? SendDataToFirstBottomSheet)?.euroFlag(R.drawable.image_1_4)
            dismiss()
        }

        americaFlag.setOnClickListener {
            (parentFragment as? SendDataToFirstBottomSheet)?.americaFlag(R.drawable.image_1_2)
            dismiss()
        }

        turkeyFlag.setOnClickListener {
            (parentFragment as? SendDataToFirstBottomSheet)?.turkeyFlag(R.drawable.image_1_3)
            dismiss()
        }

        russiaFlag.setOnClickListener {
            (parentFragment as? SendDataToFirstBottomSheet)?.russiaFlag(R.drawable.image_1_5)
            dismiss()
        }

    }

    interface SendDataToFirstBottomSheet{
        fun euroFlag(res: Int)
        fun americaFlag(res: Int)
        fun turkeyFlag(res: Int)
        fun russiaFlag(res: Int)
    }
}