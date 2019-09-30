package com.me.presentation.postlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.me.presentation.R
import com.me.presentation.di.injectFeature
import com.me.presentation.extenstions.startRefreshing
import com.me.presentation.extenstions.stopRefreshing
import com.me.presentation.model.PostItem
import com.me.presentation.model.Resource
import com.me.presentation.model.ResourceState
import com.me.presentation.postdetails.PostDetailsActivity
import kotlinx.android.synthetic.main.activity_post_list.*
import org.koin.androidx.viewmodel.ext.viewModel

class PostsListActivity : AppCompatActivity() {

    private val vm: PostListViewModel by viewModel()

    private val itemClick: (PostItem) -> Unit =
        {

            PostDetailsActivity.navigateTo(
                this@PostsListActivity,
                PostDetailsActivity::class.java, it.userId,
                it.postId
            )
        }

    private val adapter = PostListAdapter(itemClick)

    private val snackBar by lazy {
        Snackbar.make(swipeRefreshLayout, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) { vm.get(refresh = true) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        // TODO : Why calling this here ?
        injectFeature()


        if (savedInstanceState == null) {
            vm.get(refresh = false)
        }

        postsRecyclerView.adapter = adapter

        vm.posts.observe(this, Observer { updatePosts(it) })
        swipeRefreshLayout.setOnRefreshListener { vm.get(refresh = true) }
    }

    private fun updatePosts(resource: Resource<List<PostItem>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> swipeRefreshLayout.startRefreshing()
                ResourceState.SUCCESS -> swipeRefreshLayout.stopRefreshing()
                ResourceState.ERROR -> swipeRefreshLayout.stopRefreshing()
            }
            it.data?.let { adapter.submitList(it) }
            it.message?.let { snackBar.show() }
        }
    }
}
