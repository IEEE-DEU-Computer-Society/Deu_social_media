package com.example.deu_social_media

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deu_social_media.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_post.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        database= FirebaseFirestore.getInstance()
        auth=FirebaseAuth.getInstance()

        setContentView(binding.root)
        replaceFragment(PostFragment())
        rbPost.isChecked=true

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            btnNewpost.isChecked=false
            rbPost.isChecked=false
            rbCommunity.isChecked=false
            rbProfile.isChecked=false
            rbTop.isChecked=false
            when(checkedId){
                R.id.rbPost->{
                    rbPost.isChecked=true
                    replaceFragment(PostFragment())

                }
                R.id.rbCommunity->{
                    rbCommunity.isChecked=true
                    replaceFragment(ClubsFragment())
                }
                R.id.rbProfile->{
                    rbProfile.isChecked=true
                    replaceFragment(ProfileFragment())
                }
                R.id.rbTop->{
                    rbTop.isChecked=true
                    replaceFragment(FragmentTop())
                }
                R.id.btnNewpost->{
                    btnNewpost.isChecked=true
                    val intent=Intent(this,NewPostActivity::class.java)
                    startActivity(intent)
                }
            }
        }






    }
    fun replaceFragment(fragment:Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logOut -> {
                auth.signOut()
                val intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }




}