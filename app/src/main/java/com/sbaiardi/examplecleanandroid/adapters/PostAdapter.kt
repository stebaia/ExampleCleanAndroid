package com.sbaiardi.examplecleanandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sbaiardi.domain.entities.Post
import com.sbaiardi.examplecleanandroid.R
import com.sbaiardi.examplecleanandroid.entities.PostCheckingStatusEnum
import com.sbaiardi.examplecleanandroid.entities.PostWithStatus

class PostAdapter(
    private val context: Context,
    private val listener: ActionClickListener
): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val posts: ArrayList<PostWithStatus> = arrayListOf()


    interface ActionClickListener {
        fun check(post: PostWithStatus)
        fun uncheck(post: PostWithStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_post_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        posts[position].also { post ->
            post.imageUrl?.let { imageUrl ->
                Glide.with(context)
                    .load(imageUrl)
                    .into(holder.ivBookCover)
            } ?: kotlin.run {
                Glide.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .into(holder.ivBookCover)

            }
            holder.tvBookName.text = post.title
            holder.tvBookAuthors.text = post.authors.joinToString()
            holder.ivUnbookmark.setOnClickListener {
                listener.uncheck(post)
            }

            holder.ivBookmark.setOnClickListener {
                listener.check(post)
            }

            when (post.status) {
                PostCheckingStatusEnum.CHECKED -> {
                    holder.ivBookmark.visibility = View.GONE
                    holder.ivUnbookmark.visibility = View.VISIBLE
                }
                PostCheckingStatusEnum.UNCHECKED -> {
                    holder.ivBookmark.visibility = View.VISIBLE
                    holder.ivUnbookmark.visibility = View.GONE

                }
            }
        }

    }

    fun submitUpdate(update: List<PostWithStatus>) {
        val callback = PostDiffCallback(posts, update)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback)

        posts.clear()
        posts.addAll(update)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
       return posts.size
    }

    class PostDiffCallback(
        private val oldPosts: List<PostWithStatus>,
        private val newPosts: List<PostWithStatus>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldPosts.size
        }

        override fun getNewListSize(): Int {
            return newPosts.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPosts[oldItemPosition].id == newPosts[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPosts[oldItemPosition].status == newPosts[newItemPosition].status
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvBookName: TextView = view.findViewById(R.id.tvBookName)
        val tvBookAuthors: TextView = view.findViewById(R.id.tvBookAuthors)
        val ivBookCover: ImageView = view.findViewById(R.id.ivBookCover)
        val ivBookmark: ImageView = view.findViewById(R.id.ivBookmark)
        val ivUnbookmark: ImageView = view.findViewById(R.id.ivUnbookmark)
    }
}