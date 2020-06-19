package demo.simple.niceui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder

class ItemBinder(
    private val onItemClick: (item: ItemBean) -> Unit
) :ItemViewBinder<ItemBean, ItemBinder.VH>() {
    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): VH {
        return VH(inflater.inflate(R.layout.item_main, parent, false))
    }

    override fun onBindViewHolder(holder: VH, item: ItemBean) {
        holder.tvTitle.text = item.title

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)!!
    }


}