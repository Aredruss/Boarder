package com.redbox.anchan.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redbox.anchan.R
import com.redbox.anchan.network.pojo.Post
import com.redbox.anchan.page.PageFragment
import kotlinx.android.synthetic.main.thread_item_layout.view.*

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    lateinit var posts: List<Post>
    lateinit var hostFragment: PageFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.thread_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        override fun onClick(v: View?) {
            hostFragment.openThread(hostFragment.board, posts[0].no)
        }

        init {
            itemView.setOnClickListener(this)
        }

        private val numTv = itemView.post_num_tv
        private val comTv = itemView.post_reply_tv
        private val dateTv = itemView.post_time_tv
        private val postIv = itemView.post_img_iv

        fun bind(post : Post) {
            numTv.text = post.no.toString()
            dateTv.text = post.now
            comTv.text = post.com
            Glide.with(itemView.context)
                .load("https://i.4cdn.org/${hostFragment.board}/${post.tim}${post.ext}")
                .into(postIv)
        }
    }
}