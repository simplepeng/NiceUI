package demo.simple.niceui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import demo.simple.niceui.examples.BadgeViewActivity
import demo.simple.niceui.examples.DashLineActivity
import demo.simple.niceui.examples.NiceCheckBoxActivity
import demo.simple.niceui.utils.ItemBean
import demo.simple.niceui.utils.ItemBinder
import kotlinx.android.synthetic.main.activity_main.*
import me.simple.ui.BadgeTextView
import me.simple.ui.DashLineView
import me.simple.ui.CompoundImageView

class MainActivity : AppCompatActivity() {

    private val mItems = mutableListOf<ItemBean>()
    private val mAdapter = MultiTypeAdapter(mItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdapter.register(ItemBean::class, ItemBinder(onItemClick = { item ->
            val intent = Intent(this, Class.forName(item.clazz))
            startActivity(intent)
        }))

        recyclerView.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            adapter = mAdapter
        }
        addItems()
    }

    private fun addItems() {
        mItems.add(ItemBean(BadgeTextView::class.java.simpleName, BadgeViewActivity::class.java.name))
        mItems.add(ItemBean(CompoundImageView::class.java.simpleName, NiceCheckBoxActivity::class.java.name))
        mItems.add(ItemBean(DashLineView::class.java.simpleName, DashLineActivity::class.java.name))

        mAdapter.notifyDataSetChanged()
    }
}
