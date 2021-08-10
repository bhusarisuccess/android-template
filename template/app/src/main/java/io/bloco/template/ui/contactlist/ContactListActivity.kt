package io.bloco.template.ui.contactlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import io.bloco.template.R
import io.bloco.template.databinding.ActivityContactListBinding
import io.bloco.template.ui.contactdetails.ContactListDetailsActivity
import kotlinx.android.synthetic.main.activity_contact_list.*
import timber.log.Timber

class ContactListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactListBinding
    private val viewModel: ContactListViewModel by lazy {
        ViewModelProvider(this).get(ContactListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        initRecyclerViewAdapter()
        supportActionBar?.title = "Contact List"
        setupObservable()
        setupScrollListener()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initRecyclerViewAdapter() {
        binding.rvContactList.adapter = ContactListAdapter(ContactListAdapter.OnClickListener {
            Timber.d("Selected Contact $it")
            val intent = Intent(this@ContactListActivity, ContactListDetailsActivity::class.java)
            intent.putExtra("CONTENT_ID", it.id)
            intent.putExtra("CONTACT_IMAGE", it.thumbnail)
            intent.putExtra("CONTACT_NAME", it.name)
            intent.putExtra("CONTACT_PHONE", it.phone)
            intent.putExtra("CONTACT_EMAIL", it.email)
            intent.putExtra("CONTACT_IS_STARED", it.isStarred)
            startActivity(intent)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.initContactList()
    }

    private fun setupObservable() {
        viewModel.loadingState.observe(this, Observer {
            it?.run {
                when (it) {
                    LoadingState.DONE -> {
                        binding.progressBarLoading.visibility = View.GONE
                    }
                    LoadingState.ERROR -> {
                        binding.progressBarLoading.visibility = View.GONE
                    }
                    LoadingState.LOADING -> {
                        binding.progressBarLoading.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupScrollListener() {
        val layoutManager =
            binding.rvContactList.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
        binding.rvContactList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val totalItemCount = layoutManager.itemCount
                    val visibleItemCount = layoutManager.childCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
                }
            }
        })

    }
}