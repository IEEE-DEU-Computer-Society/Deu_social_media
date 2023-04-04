package com.example.deu_social_media

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_new_post.view.*
import kotlinx.android.synthetic.main.post_view.view.*

class PostRecyclerAdapter(val postList:ArrayList<Post>):RecyclerView.Adapter<PostRecyclerAdapter.PostHolder>() {
    class PostHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var etPostNick :TextView= itemView.findViewById<TextView>(R.id.etPostNick)

        var postText:TextView = itemView.findViewById<TextView>(R.id.postText)
        var btnLike:Button= itemView.findViewById<Button>(R.id.btnLike)
        var btnDislike:Button = itemView.findViewById<Button>(R.id.btnDislike)
        var btnComment :Button= itemView.findViewById<Button>(R.id.btnComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflate=LayoutInflater.from(parent.context)
        val view=inflate.inflate(R.layout.post_view,parent,false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.etPostNick.setText(postList[position].nick)
        holder.postText.setText(postList[position].postText)
        holder.btnLike.setText("Beğen (${postList[position].likeNumber.toString()})")
        holder.btnDislike.setText("Beğenme (${postList[position].dislikeNumber.toString()})")
        holder.btnComment.setText("Yorum (${postList[position].commentsNumber.toString()})")

    }
}