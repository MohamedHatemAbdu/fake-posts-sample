package com.me.presentation.postdetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.me.presentation.R
import com.me.presentation.di.injectFeature
import com.me.presentation.extenstions.gone
import com.me.presentation.extenstions.loadAvatar
import com.me.presentation.extenstions.visible
import com.me.presentation.helpers.Constants
import com.me.presentation.model.CommentItem
import com.me.presentation.model.PostItem
import com.me.presentation.model.Resource
import com.me.presentation.model.ResourceState
import kotlinx.android.synthetic.main.activity_post_details.*
import kotlinx.android.synthetic.main.include_user_info.*
import kotlinx.android.synthetic.main.item_list_post.*
import org.koin.androidx.viewmodel.ext.viewModel

class PostDetailsActivity : AppCompatActivity() {

    private val vm: PostDetailsViewModel by viewModel()
    private val adapter = CommentsAdapter()
    private val userId by lazy { intent.getStringExtra(Constants.USER_ID_KEY) }
    private val postId by lazy { intent.getStringExtra(Constants.POST_ID_KEY) }
    private val snackBar by lazy {
        Snackbar.make(container, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) { vm.getComments(postId, refresh = true) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        injectFeature()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        commentsRecyclerView.isNestedScrollingEnabled = false
        commentsRecyclerView.adapter = adapter

        if (savedInstanceState == null) {
            vm.getPost(UserIdPostId(userId, postId))
            vm.getComments(postId, refresh = false)
        }

        vm.post.observe(this, Observer { updatePost(it) })
        vm.comments.observe(this, Observer { updateComments(it) })

    }

    private fun updatePost(postItem: PostItem?) {
        postItem?.let {
            userAvatar.loadAvatar(it.email)
            userUsername.text = "@${it.username}"
            userName.text = it.name
            postTitle.text = it.title.capitalize()
            postBody.maxLines = Int.MAX_VALUE
            postBody.text = it.body.capitalize()
        }
    }

    private fun updateComments(resource: Resource<List<CommentItem>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> progressBar.visible()
                ResourceState.SUCCESS -> progressBar.gone()
                ResourceState.ERROR -> progressBar.gone()
            }
            it.data?.let { adapter.submitList(it) }
            it.message?.let { snackBar.show() }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {

        fun <T> navigateTo(from: Activity, to: Class<T>, userId: String, postId: String) {
            val intentToDetails = Intent(from, to)
            intentToDetails.putExtra(Constants.USER_ID_KEY, userId)
            intentToDetails.putExtra(Constants.POST_ID_KEY, postId)
            from.startActivity(intentToDetails)
        }
    }
}