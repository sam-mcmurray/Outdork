package dev.samuelmcmurray.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.share.widget.ShareButton
import dev.samuelmcmurray.R

class PostAdapter(private val list: List<Post>) : RecyclerView.Adapter<PostViewHolder>() {

    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PostViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post: Post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size


}