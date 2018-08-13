package io.github.louistsaitszho.louisshutterstock

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import io.github.louistsaitszho.louisshutterstock.model.Image
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_photo.*

class ImageViewHolder(
        override val containerView: View?
): RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(image: Image) {
        Glide.with(image_view)
                .load(image.url)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                )
                .into(image_view)
    }
}

class ImagesAdapter: RecyclerView.Adapter<ImageViewHolder>() {
    private var images: List<Image> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
            ImageViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.viewholder_photo, parent, false)
            )

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun resetImages(newList: List<Image>) {
        images = newList
        notifyDataSetChanged()
    }
}