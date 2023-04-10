package com.example.deu_social_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import com.example.deu_social_media.databinding.ActivityNewPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class NewPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewPostBinding
    private lateinit var database:FirebaseFirestore
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database= FirebaseFirestore.getInstance()
        auth=FirebaseAuth.getInstance()

        binding= ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun share(v:View){
        var postText=binding.etPost.text.toString()
        if (!postText.isNullOrBlank()){
            var nick:String?=null
            val email=auth.currentUser!!.email.toString()
            database.collection("nicks").whereEqualTo("email",email).addSnapshotListener { value, error ->
                if(error!=null){
                    Toast.makeText(applicationContext,error.localizedMessage,Toast.LENGTH_LONG)
                }
                else{
                    if(value!=null && !value.isEmpty){
                        val nicks=value.documents

                        for(it in nicks){
                            if(it.get("email")!!.equals(email)){
                                nick= it.get("nick") as String?
                                break
                            }

                        }
                        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                        val currentDate = sdf.format(Date())

                        var hash=HashMap<String,Any>()
                        var comments=java.util.HashMap<String,Any>()
                        var id=UUID.randomUUID()
                        hash.put("nick",nick.toString())
                        hash.put("postText",postText)
                        hash.put("likeNumber","0")
                        hash.put("dislikeNumber","0")
                        hash.put("comments",comments)
                        hash.put("commentsNumber","0")
                        hash.put("date",currentDate)

                        database.collection("Post").add(hash).addOnCompleteListener {
                            if(it.isSuccessful){
                                println("başarıyla yüklendi")

                                finish()
                            }

                        }.addOnFailureListener {
                            Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG)
                        }


                    }
                }

            }








        }
    }
    /*private fun findNick(email:String):String?{
        var retStr:String?=null
        database.collection("nicks").addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(applicationContext,error.localizedMessage,Toast.LENGTH_LONG)
            }
            else{
                if(value!=null && !value.isEmpty){
                    val nicks=value.documents

                    for(nick in nicks){
                        if(nick.get("email")!!.equals(email)){
                            retStr= nick.get("nick") as String?
                            break
                        }

                    }
                }
            }

        }

    }*/
}