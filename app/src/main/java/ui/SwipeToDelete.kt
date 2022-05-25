package ui

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDelete(var adapter: Adapter): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)
{
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var position = viewHolder.adapterPosition
        adapter.deleteItem(position)
    }

}