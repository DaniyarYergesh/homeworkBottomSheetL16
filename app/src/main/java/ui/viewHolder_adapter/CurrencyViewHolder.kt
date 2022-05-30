package ui.viewHolder_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_recyclerview.Currency
import com.example.homework_recyclerview.R
import com.google.android.material.textfield.TextInputEditText

class CurrencyViewHolder(inflater: LayoutInflater, parent: ViewGroup, private var function:(Currency, Int)-> Unit)
    :RecyclerView.ViewHolder(inflater.inflate(R.layout.currency_recyclerview_layout, parent, false))
{
    private val currencyTextTextView = itemView.findViewById<TextInputEditText>(R.id.currencyText)
    private val currencyTypeTextView = itemView.findViewById<TextView>(R.id.currencyType)
    private val currencyFlagView = itemView.findViewById<ImageView>(R.id.currencyFlag)


    fun bind(item: Currency, position: Int){
        currencyTextTextView.setText(item.text.toString())
        currencyTypeTextView.text = item.type
        currencyFlagView.setBackgroundResource(item.flag)
        currencyFlagView.setOnLongClickListener {
            function(item, position)
            true
        }
    }
}