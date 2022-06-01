package ui.viewHolder_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_recyclerview.*
import com.example.homework_recyclerview.Currency

import model.Parent

class Adapter(
    private val clickListener: () -> Unit,
    private val function: (Currency, Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = mutableListOf<Parent>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.currency_recyclerview_layout -> CurrencyViewHolder(inflater, parent, function)
            R.layout.add_button_recyclerview_layout -> AddButtonViewHolder(inflater, parent, clickListener)
            else -> CurrencyViewHolder(inflater, parent, function)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (data[position]) {
            is Currency -> R.layout.currency_recyclerview_layout
            is Add1 -> R.layout.add_button_recyclerview_layout
            else -> R.layout.currency_recyclerview_layout
        }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrencyViewHolder -> holder.bind(data[position] as Currency, position)
            is AddButtonViewHolder -> holder.bind(data[position] as Add1)
        }
    }


    fun setItems(list: List<Parent>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun addNewItem(newItem: Parent, position: Int) {
        data.add(position, newItem as Currency)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun deleteCurrency(currency: Currency, position: Int) {
        data.remove(currency)
        notifyDataSetChanged()
    }

    fun moveItem(from: Int, to: Int) {
        val fromEmoji = data[from]
        data.removeAt(from)
        if (to < from) {
            data.add(to, fromEmoji)
        } else {
            data.add(to - 1, fromEmoji)
        }
        notifyItemMoved(from, to)
    }


    fun sortByName() {
        val data1 = data.dropLast(1) as MutableList<Currency>
        data1.sortBy { it.type }
        val button: Parent = data.last()
        val items = mutableListOf<Parent>()
        items.addAll(0, data1)
        items.add(button)
        setItems(items)
    }

    fun sortByPrice() {
        val data1 = data.dropLast(1) as MutableList<Currency>
        data1.sortBy { it.text }
        val button: Parent = data.last()
        val items = mutableListOf<Parent>()
        items.addAll(0, data1)
        items.add(button)
        setItems(items)
    }

    fun getPositionType(newItem: Parent): Int {
        val data1 = data.dropLast(1) as MutableList<Currency>
        data1.add(newItem as Currency)
        data1.sortBy { it.type }
        return data1.indexOf(newItem)
    }

    fun getPositionName(newItem: Parent): Int {
        val data1 = data.dropLast(1) as MutableList<Currency>
        data1.add(newItem as Currency)
        data1.sortBy { it.text }
        return data1.indexOf(newItem)
    }

    fun updateCurrencyData(textTenge: String) {
        val data1 = data.dropLast(1) as MutableList<Currency>
        for (item in data1){
            item.text = textTenge.toInt() / item.course
            notifyDataSetChanged()
        }

    }

}