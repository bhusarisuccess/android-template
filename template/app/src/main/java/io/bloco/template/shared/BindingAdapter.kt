package io.bloco.template.shared

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.bloco.template.BuildConfig
import io.bloco.template.R
import io.bloco.template.domain.model.Content
import io.bloco.template.ui.contactlist.ContactListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("contactListAdapter")
fun bindContactListAdapter(recyclerView: RecyclerView, data: List<Content>?) {
    val adapter = recyclerView.adapter as ContactListAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageViewBinding")
fun bindImageViewImageRounded(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imagePath = "${BuildConfig.BASE_URL}${imgUrl}"
        Glide.with(imgView.context)
            .load(imagePath).
                placeholder(imgView.context.resources.getDrawable(R.drawable.profile_placeholder))
            .apply(
                RequestOptions()
            )
            .into(imgView)
    }
}

@BindingAdapter("heartTintBinding")
fun bindHeartTintBinding(imgView: ImageView, data: Int?) {
    val context = imgView.context
    if (data == 0) {
        imgView.setColorFilter(ContextCompat.getColor(context, R.color.gray))
    } else {
        imgView.setColorFilter(ContextCompat.getColor(context, R.color.red))
    }

}