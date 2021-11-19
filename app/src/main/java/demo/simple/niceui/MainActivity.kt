package demo.simple.niceui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import demo.simple.niceui.databinding.ActivityMainBinding
import demo.simple.niceui.examples.*
import demo.simple.niceui.utils.ItemBean
import demo.simple.niceui.utils.ItemBinder
import me.simple.ui.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(this.layoutInflater) }

    private val mItems = mutableListOf<ItemBean>()
    private val mAdapter = MultiTypeAdapter(mItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mAdapter.register(ItemBean::class, ItemBinder(onItemClick = { item ->
            val intent = Intent(this, Class.forName(item.clazz))
            startActivity(intent)
        }))

        binding.recyclerView.run {
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
                BadgeTextView::class.java.simpleName,
                BadgeViewActivity::class.java.name
            )
        )
        mItems.add(
            ItemBean(
                CompoundImageView::class.java.simpleName,
                ImageCheckBoxActivity::class.java.name
            )
        )
        mItems.add(
            ItemBean(
                DashLineView::class.java.simpleName,
                DashLineViewActivity::class.java.name
            )
        )
        mItems.add(
            ItemBean(
                SquareLayout::class.java.simpleName,
                SquareLayoutActivity::class.java.name
            )
        )
        mItems.add(
            ItemBean(
                FlowRadioGroup::class.java.simpleName,
                FlowRadioGroupActivity::class.java.name
            )
        )

        mAdapter.notifyDataSetChanged()
    }
}
