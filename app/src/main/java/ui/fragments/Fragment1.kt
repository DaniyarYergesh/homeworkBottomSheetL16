package ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_recyclerview.Add1
import com.example.homework_recyclerview.Currency
import com.example.homework_recyclerview.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import model.Parent
import ui.DeleteDialogCallback
import ui.DeleteDialogFragment
import ui.bottomSheet_Dialog.BottomSheetDialog
import ui.bottomSheet_Dialog.SelectCurrencyBottomSheet
import ui.itemTouchHelper.DragDropMove
import ui.itemTouchHelper.SwipeToDelete
import ui.viewHolder_adapter.Adapter
import kotlin.random.Random

class Fragment1 : Fragment(R.layout.fragment_1), DeleteDialogCallback,
    BottomSheetDialog.SecondBottomSheet {

    private var deletedCurrency: Currency? = null
    private var positionOfDeletedItem: Int? = null
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private var chosenIndex = -1
    private var adapter: Adapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var currencyList: List<Parent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.main_menu)
        setupFun()
        onOptionsItemSelected1()
    }

    private fun setupFun() {
        currencyList = listOf(
            Currency(10000, "Доллары, США ", R.drawable.image_1_2),
            Currency(1000, "Лира, Турция", R.drawable.image_1_3),
            Currency(100000, "Евро, EC", R.drawable.image_1_4),
            Currency(1000000, "Доллары, США", R.drawable.image_1_5),
            Currency(3000, "Доллары, США", R.drawable.image_1_2),
            Currency(1500, "Доллары, США", R.drawable.image_1_2),
            Currency(56000000, "Лира, Турция", R.drawable.image_1_3),
            Currency(23450, "Евро, EC", R.drawable.image_1_4),
            Add1("Добавить", R.drawable.path837)
        )

        val myLambda: () -> Unit =
            {
                BottomSheetDialog().show(childFragmentManager, null)
            }

        val myLambda2: (Currency, Int) -> Unit = { item, position ->
            deletedCurrency = item
            positionOfDeletedItem = position

            toolbar.setTitle("$position Item Selected")
            toolbar.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.toolbarColor
                )
            )
            toolbar.menu.findItem(R.id.menu_del).isVisible = true
            toolbar.menu.findItem(R.id.sorting_by).isVisible = false
            toolbar.menu.findItem(R.id.drop_sorting).isVisible = false
            Log.i("MainActivity", "${deletedCurrency}")

        }

        adapter = Adapter(myLambda, myLambda2)

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val myRecyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerView)

        myRecyclerView.adapter = adapter
        myRecyclerView.layoutManager = layoutManager

        adapter?.setItems(currencyList)

        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter!!))
        itemTouchHelper.attachToRecyclerView(myRecyclerView)

        val itemTouchHelper1 = ItemTouchHelper(DragDropMove(adapter!!))
        itemTouchHelper1.attachToRecyclerView(myRecyclerView)

    }

    lateinit var dialog: DialogFragment

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val chosenMenuItemId = when (chosenIndex) {
            0 -> R.id.alphabet
            1 -> R.id.currency
            else -> 0
        }
        menu.findItem(chosenMenuItemId).isChecked = true
        super.onPrepareOptionsMenu(menu)
    }

    private fun onOptionsItemSelected1() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.alphabet -> {
                    chosenIndex = 0
                    adapter?.sortByName()
                    true
                }
                R.id.currency -> {
                    chosenIndex = 1
                    adapter?.sortByPrice()
                    true
                }
                R.id.drop_sorting -> {
                    adapter?.setItems(currencyList)
                    true
                }
                R.id.menu_del -> {
                    deleteItems()
                    true
                }
                else -> false
            }
        }

    }

    fun scrollBottom(n: Int) {
        val smoothScroller = object : LinearSmoothScroller(requireContext()) {
            override fun getVerticalSnapPreference(): Int = LinearSmoothScroller.SNAP_TO_START
        }
        smoothScroller.targetPosition = n
        layoutManager?.startSmoothScroll(smoothScroller) // плавная прокрутка
    }

    private fun deleteItems() {
        dialog = DeleteDialogFragment()
        dialog.show(childFragmentManager, null)
        toolbar.setTitle("Converter")
        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar.menu.findItem(R.id.menu_del).isVisible = false
        toolbar.menu.findItem(R.id.sorting_by).isVisible = true
        toolbar.menu.findItem(R.id.drop_sorting).isVisible = true
    }

    override fun onDeleteButton() {
        adapter?.deleteCurrency(deletedCurrency!!, positionOfDeletedItem!!)
        Snackbar.make(
            requireView().findViewById(R.id.recyclerView),
            "Item Deleted",
            Snackbar.LENGTH_SHORT
        )
            .setAction("Undo") {
                adapter?.addNewItem(deletedCurrency!!, positionOfDeletedItem!!)
            }
            .show()
    }

    override fun openSecondBottomSheet(fragmentManager: FragmentManager) {
        SelectCurrencyBottomSheet().show(fragmentManager, null)
    }

    override fun addNewItemFromBottomSheet(
        nameOfCurrency: TextInputEditText,
        costRespectiveToTenge: TextInputEditText,
        res: Int
    ) {
        var position = 0
        val random: Int = Random.nextInt(9)
        if (chosenIndex == -1) {
            position = adapter?.data!!.size - 1
        }
        if (chosenIndex == 0) {
            position = adapter?.getPositionType(currencyList[random]) ?: 0
        }
        if (chosenIndex == 1) {
            position = adapter?.getPositionName(currencyList[random]) ?: 0
        }
        adapter?.addNewItem(
            Currency(
                Integer.parseInt(costRespectiveToTenge.text.toString()),
                nameOfCurrency.text.toString(),
                res
            ), position
        )
        scrollBottom(adapter?.itemCount ?: 0)
    }


}