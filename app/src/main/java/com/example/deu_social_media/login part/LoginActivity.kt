package com.example.deu_social_media

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    //ELİF ARAS Authentication için firebase bağlantısı
    var mAuth: FirebaseAuth? = null;
    //ELİF ARAS student için firestore bağlantısı
    var firebaseFirestoreDb: FirebaseFirestore? = null;
    var collectionReference: CollectionReference? = null;


    override fun onCreate(savedInstanceState: Bundle?) {

        mAuth = FirebaseAuth.getInstance();
        //ELİF ARAS student için firestore collection bağlantısı
        collectionReference = firebaseFirestoreDb?.collection("students");

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    fun goToregister(v:View){
        var action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        Navigation.findNavController(v).navigate(action)
    }
    fun register(v:View){
        var nick=findViewById<EditText>(R.id.etNick).text.toString()
        var email=findViewById<EditText>(R.id.etEmail).text.toString()
        var password1=findViewById<EditText>(R.id.etPassword1).text.toString()
        var password2=findViewById<EditText>(R.id.etPassword2).text.toString()
        if (!nick.isNullOrBlank() && !email.isNullOrBlank() && !password1.isNullOrBlank() && !password2.isNullOrBlank()){
            if(password1==password2){
                /* authentication part*/
                /* if it pass
                *
                * */
                val action=RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                Navigation.findNavController(v).navigate(action)


            }else{
                Toast.makeText(this,"Your passwords don't match",Toast.LENGTH_LONG).show()
            }



        }else{
            Toast.makeText(this,"Please fill all boxes",Toast.LENGTH_LONG).show()
        }



    }
    fun login(v:View){
        var email=findViewById<EditText>(R.id.etEmailLogin).text.toString()
        var password=findViewById<EditText>(R.id.etPasswordLogin).text.toString()
        if (!email.isNullOrBlank() && !password.isNullOrBlank()){

            /* authentication part*/
            /* if it pass
            *
            * */
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)

        }else{
            Toast.makeText(this,"Please fill all boxes",Toast.LENGTH_LONG).show()
        }
    }

}