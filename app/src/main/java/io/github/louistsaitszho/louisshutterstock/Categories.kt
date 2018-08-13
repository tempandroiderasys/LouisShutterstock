package io.github.louistsaitszho.louisshutterstock

import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.louistsaitszho.louisshutterstock.model.Category
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_category.*


class CategoriesAdapter: RecyclerView.Adapter<CategoriesViewHolder>() {
    private var items: List<Category> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.viewholder_category, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun resetList(newList: List<Category>) {
        val diffResult = DiffUtil.calculateDiff(CategoriesDiffCallback(items, newList))
        diffResult.dispatchUpdatesTo(this)
        items = newList
    }
}

class CategoriesDiffCallback(
        private val oldList: List<Category>,
        private val newList: List<Category>
): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
}


class CategoriesViewHolder(
        override val containerView: View?
): RecyclerView.ViewHolder(containerView), LayoutContainer {
    val TAG = "CategoriesViewHolder"
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(containerView?.context, LinearLayoutManager.HORIZONTAL, false)
    }
    private val adapter = ImagesAdapter()

    fun bind(category: Category) {
        if (recycler_view_images.adapter == null) {
            recycler_view_images.layoutManager = linearLayoutManager
            recycler_view_images.adapter = adapter
        }

        text_view_title.text = category.name
        adapter.resetImages(category.images)
    }
}