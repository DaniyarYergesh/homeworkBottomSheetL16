package ui.bottomSheet_Dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.example.homework_recyclerview.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class BottomSheetDialog: BottomSheetDialogFragment(), SelectCurrencyBottomSheet.SendDataToFirstBottomSheet{

    lateinit var nameOfCurrency: TextInputEditText
    lateinit var costRespectiveToTenge: TextInputEditText
    var respectiveFlag: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameOfCurrency = view.findViewById(R.id.typeCurrencyType)
        costRespectiveToTenge = view.findViewById(R.id.typeCurrencyCost)
        val selectFlagButton = view.findViewById<Button>(R.id.selectCurrencyFlag)
        val addCurrencyButton = view.findViewById<Button>(R.id.addNewCurrency)


        selectFlagButton.setOnClickListener {
            (parentFragment as? SecondBottomSheet)?.openSecondBottomSheet(childFragmentManager)
        }

        addCurrencyButton.setOnClickListener {
            (parentFragment as? SecondBottomSheet)?.addNewItemFromBottomSheet(nameOfCurrency, costRespectiveToTenge, respectiveFlag!!)
            dismiss()
        }

    }

    interface SecondBottomSheet{
        fun openSecondBottomSheet(fragmentManager: FragmentManager)
        fun addNewItemFromBottomSheet(nameOfCurrency: TextInputEditText, costRespectiveToTenge:TextInputEditText, res: Int)
    }

    override fun euroFlag(res: Int) {
        Log.i("Tag","$res")
        nameOfCurrency.setText("Евро, EC")
        respectiveFlag = res
    }

    override fun americaFlag(res: Int) {
        nameOfCurrency.setText("Доллары, США")
        respectiveFlag = res
    }

    override fun turkeyFlag(res: Int) {
        nameOfCurrency.setText("Лира, Турция")
        respectiveFlag = res
    }

    override fun russiaFlag(res: Int) {
        nameOfCurrency.setText("Рубль, Россия")
        respectiveFlag = res
    }

}