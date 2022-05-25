package ui


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.*
import com.example.homework_recyclerview.Add1
import com.example.homework_recyclerview.Currency
import com.example.homework_recyclerview.R
import com.google.android.material.snackbar.Snackbar
import model.Parent
import kotlin.random.Random


class MainActivity : AppCompatActivity(R.layout.activity_main), DeleteDialogCallback{


    private var deletedCurrency: Currency? = null
    private var positionOfDeletedItem: Int? = null
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private var chosenIndex = -1
    private var adapter: Adapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var currencyList: List<Parent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupFun()
    }

    private fun setupFun() {
        currencyList = listOf(
            Currency(1500000, "Тенге, Казахстан ", R.drawable.image_1),
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
                adapter?.addNewItem(currencyList[random], position)
                scrollBottom(adapter?.itemCount ?: 0)
            }

        val myLambda2: (Currency, Int) -> Unit = {item, position ->
            deletedCurrency = item
            positionOfDeletedItem = position

            toolbar.setTitle("$position Item Selected")
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.toolbarColor))
            toolbar.menu.findItem(R.id.menu_del).isVisible = true
            toolbar.menu.findItem(R.id.sorting_by).isVisible = false
            toolbar.menu.findItem(R.id.drop_sorting).isVisible = false
            Log.i("MainActivity", "${deletedCurrency}")

        }

        adapter = Adapter(myLambda, myLambda2)

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val myRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        myRecyclerView.adapter = adapter
        myRecyclerView.layoutManager = layoutManager

        adapter?.setItems(currencyList)

        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter!!))
        itemTouchHelper.attachToRecyclerView(myRecyclerView)

        val itemTouchHelper1 = ItemTouchHelper(DragDropMove(adapter!!))
        itemTouchHelper1.attachToRecyclerView(myRecyclerView)


    }

    lateinit var dialog: DialogFragment

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val chosenMenuItemId = when (chosenIndex) {
            0 -> R.id.alphabet
            1 -> R.id.currency
            else -> return super.onPrepareOptionsMenu(menu)
        }
        menu?.findItem(chosenMenuItemId)?.isChecked = true
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
            else -> return super.onOptionsItemSelected(item)
        }
    }


    fun scrollBottom(n: Int) {
        val smoothScroller = object : LinearSmoothScroller(this) {
            override fun getVerticalSnapPreference(): Int = LinearSmoothScroller.SNAP_TO_START
        }
        smoothScroller.targetPosition = n
        layoutManager?.startSmoothScroll(smoothScroller) // плавная прокрутка
    }


    private fun deleteItems(){
        dialog = DeleteDialogFragment()
        dialog.show(supportFragmentManager, null)
        toolbar.setTitle("Converter")
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        toolbar.menu.findItem(R.id.menu_del).isVisible = false
        toolbar.menu.findItem(R.id.sorting_by).isVisible = true
        toolbar.menu.findItem(R.id.drop_sorting).isVisible = true
    }

    override fun onDeleteButton() {
        adapter?.deleteCurrency(deletedCurrency!!, positionOfDeletedItem!!)

        Snackbar.make(findViewById(R.id.recyclerView), "Item Deleted", Snackbar.LENGTH_SHORT)
            .setAction("Undo" ){
                adapter?.addNewItem(deletedCurrency!!, positionOfDeletedItem!!)
            }
            .show()
    }


}