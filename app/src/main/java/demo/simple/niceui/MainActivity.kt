package demo.simple.niceui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import demo.simple.niceui.examples.BadgeViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.simple.ui.NiceBadgeView
import kotlin.reflect.KClass

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
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = mAdapter
        }
        addItems()
    }

    private fun addItems() {
        mItems.add(
            ItemBean(
                NiceBadgeView::class.java.simpleName,
                BadgeViewActivity::class.java.name
            )
        )

        mAdapter.notifyDataSetChanged()
    }
}
