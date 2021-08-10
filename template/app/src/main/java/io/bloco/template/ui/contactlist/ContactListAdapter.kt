package io.bloco.template.ui.contactlist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.bloco.template.databinding.ContactListItemBinding
import io.bloco.template.domain.model.Content

class ContactListAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Content, ContactListAdapter.ContactListViewHolder>(DiffCallback) {

    class ContactListViewHolder(private var binding: ContactListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(content: Content) {
            binding.property = content
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [VideoEntity]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.isStarred == newItem.isStarred
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactListViewHolder {
        return ContactListViewHolder(
            ContactListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        val content = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(content)
        }
        holder.bind(content)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [VideoEntity]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [VideoEntity]
     */
    class OnClickListener(val clickListener: (content: Content) -> Unit) {
        fun onClick(content: Content) = clickListener(content)
    }
}