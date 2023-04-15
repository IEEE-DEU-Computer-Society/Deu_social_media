package com.example.deu_social_media

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_post.*




class PostFragment : Fragment() {
    private lateinit var database: FirebaseFirestore
    private var postList=ArrayList<Post>()
    private lateinit var recyclerAdapter: PostRecyclerAdapter
    private lateinit var rvPost:RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        database= FirebaseFirestore.getInstance()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager=LinearLayoutManager(context)
        refreshData()
        rvPost=view.findViewById(R.id.postFragmentRv)
        rvPost.layoutManager=layoutManager

        recyclerAdapter= PostRecyclerAdapter(postList)
        rvPost.adapter=recyclerAdapter




    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)





    }
    private fun refreshData(){
        database.collection("Post").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(activity,error.localizedMessage, Toast.LENGTH_LONG)

            }else{
                if(value!=null && !value.isEmpty){
                    val posts=value!!.documents
                    postList.clear()
                    for(it in posts){
                        if(it.get("postText") as String !="test text5"){

                                var nick=it.get("nick") as String
                                var postText=it.get("postText") as String
                                var likeNumber=it.get("likeNumber") as String
                                var dislikeNumber=it.get("dislikeNumber") as String
                                var comments=it.get("comments") as HashMap<String, Any>
                                var commentsNumber=it.get("commentsNumber") as String
                                var date=it.get("date") as String
                                var id=(it.get("id") ).toString()


                                var newPost=Post(
                                    nick,postText,likeNumber, dislikeNumber, comments, commentsNumber,date,id
                                )
                                postList.add(newPost)
                                println("başrıyla yenilendi")

                        }
                    }
                    recyclerAdapter.notifyDataSetChanged()

                }else{
                    println("there is an error occured")
                }
            }
        }
    }


}