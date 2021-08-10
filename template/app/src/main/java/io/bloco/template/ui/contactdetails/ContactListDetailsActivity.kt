package io.bloco.template.ui.contactdetails

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.bloco.template.R
import io.bloco.template.databinding.ActivityContactListDetailsBinding
import io.bloco.template.shared.bindImageViewImageRounded
import io.bloco.template.ui.contactlist.LoadingState
import timber.log.Timber

class ContactListDetailsActivity : AppCompatActivity() {

    private var id: Int = 0
    private var isStared: Int = 0
    private lateinit var binding: ActivityContactListDetailsBinding
    private val viewModel: ContactListDetailViewModel by lazy {
        ViewModelProvider(this).get(ContactListDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Contact Details"
        setIntentData()
        setStarredClickListener()
        setupObservable()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.d("onOptionsItemSelected ${item.itemId} ${android.R.id.home}")
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list_details)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setIntentData() {
        id = intent.getIntExtra("CONTENT_ID", 0)
        val image = intent.getStringExtra("CONTACT_IMAGE")
        val name = intent.getStringExtra("CONTACT_NAME")
        val phone = intent.getStringExtra("CONTACT_PHONE")
        val email = intent.getStringExtra("CONTACT_EMAIL")
        isStared = intent.getIntExtra("CONTACT_IS_STARED", 0)

        bindImageViewImageRounded(binding.ivAvatar, image)
        binding.tvContactName.text = name
        binding.tvContactNo.text = phone
        binding.tvContactEmail.text = email
        if (isStared == 1) {
            binding.ivHeart.setColorFilter(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.ivHeart.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        }
    }

    private fun setStarredClickListener() {
        binding.ivHeart.setOnClickListener {
            if (isStared == 1) {
                viewModel.unStarred(id)
            } else {
                viewModel.starred(id)
            }
        }

    }

    private fun setupObservable() {
        viewModel.isStaredChange.observe(this, Observer {
            if (it == 1) {
                binding.ivHeart.setColorFilter(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.ivHeart.setColorFilter(ContextCompat.getColor(this, R.color.gray))
            }
        })

        viewModel.loadingState.observe(this, Observer {
            it?.run {
                when (it) {
                    LoadingState.DONE -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    LoadingState.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    LoadingState.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}