package demo.simple.niceui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mItems = mutableListOf<MainItem>()
    private val mAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_main.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }

        initData()
    }

    private fun initData() {
        mItems.add(MainItem("BadgeView", BadgeActivity::class.java))
        mAdapter.notifyDataSetChanged()
    }

    data class MainItem(
        val title: String,
        val clazz: Class<*>
    )

    inner class MainAdapter : RecyclerView.Adapter<VH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return VH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = mItems[position]
            holder.tvItem.text = item.title
            holder.itemView.setOnClickListener {
                startActivity(Intent(this@MainActivity, item.clazz))
            }
        }

    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItem = itemView.findViewById<TextView>(R.id.tv_item)
    }
}
