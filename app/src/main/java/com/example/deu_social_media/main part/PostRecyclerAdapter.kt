package com.example.deu_social_media

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_new_post.view.*
import kotlinx.android.synthetic.main.post_view.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PostRecyclerAdapter(val postList:ArrayList<Post>):RecyclerView.Adapter<PostRecyclerAdapter.PostHolder>() {
    private var database=FirebaseFirestore.getInstance()
    private var auth=FirebaseAuth.getInstance()

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
        holder.btnLike.setOnClickListener {
            postList[position].likeNumber=(Integer.parseInt(postList[position].likeNumber) +1).toString()
            holder.btnLike.setText("Beğen (${postList[position].likeNumber.toString()})")

            refreshPost(postList[position])
        }
        holder.btnDislike.setOnClickListener {
            postList[position].dislikeNumber=(Integer.parseInt(postList[position].dislikeNumber)  +1).toString()
            holder.btnDislike.setText("Beğenme (${postList[position].dislikeNumber.toString()})")

            refreshPost(postList[position])
        }

    }
    fun refreshPost(post:Post){

        var hash=HashMap<String,Any>()
        hash.put("nick",post.nick)
        hash.put("postText",post.postText)
        hash.put("likeNumber",post.likeNumber)
        hash.put("dislikeNumber",post.dislikeNumber)
        hash.put("comments",post.comments)
        hash.put("commentsNumber",post.commentsNumber)
        hash.put("date",post.date)
        hash.put("id",post.id)
        database.collection("Post").document(post.id) .update(hash).addOnFailureListener {
            if(it!=null){
                it.printStackTrace()
            }
        }
    }

}