package com.example.deu_social_media

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.deu_social_media.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.UUID

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding


    //ELİF ARAS Authentication için firebase bağlantısı
    var mAuth: FirebaseAuth? = null;

    //ELİF ARAS student için firestore bağlantısı
    var firebaseFirestoreDb: FirebaseFirestore? = null;
    var collectionReference: CollectionReference? = null;

    lateinit var nicksRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestoreDb=FirebaseFirestore.getInstance()
        //ELİF ARAS student için firestore collection bağlantısı
        collectionReference = firebaseFirestoreDb?.collection("students");

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var currentUser= mAuth!!.currentUser
        if (currentUser!=null){
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }





    }

    fun goToregister(v: View) {
        var action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        Navigation.findNavController(v).navigate(action)
    }
     fun register(v:View){
        var nick=etPostNick.text.toString()
        var email=etEmail.text.toString()
        var password1=etPassword1.text.toString()
        var password2=etPassword2.text.toString()
        var empty=false
        if (nick.isNullOrBlank()){
            empty=true
            etPostNick.error="Nick kısmı boş geçilemez"
        }
        if(email.isNullOrBlank()) {
            empty = true
            etEmail.error="E-mail kısmı boş geçilemez"

        }
        if(password1.isNullOrBlank()){
            empty=true
            etPassword1.error="Şifre kısmı boş geçilemez"
        }
        if(password2.isNullOrBlank()){
            empty=true
            etPassword2.error="Şifre kısmı boş geçilemez"
        }



        if (!empty){
            nick=nick.toLowerCase()
            if(password1.equals(password2)){
                firebaseFirestoreDb!!.collection("nicks").whereEqualTo("nick",nick).addSnapshotListener { value, error ->
                    if (error!=null){
                        Toast.makeText(applicationContext,error.localizedMessage,Toast.LENGTH_LONG)
                    }
                    else{
                        if(value==null || value.isEmpty){
                            var hash=HashMap<String,Any>()

                            hash.put("nick",nick)
                            hash.put("email",email)

                            firebaseFirestoreDb!!.collection("nicks").add(hash).addOnCompleteListener {
                                if(it.isSuccessful){
                                    mAuth!!.createUserWithEmailAndPassword(email,password1).addOnCompleteListener {
                                        if (it.isSuccessful){

                                            val action=RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                                            Navigation.findNavController(v).navigate(action)
                                          }
                                    }.addOnFailureListener {
                                        Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG).show()
                                    }

                                }
                            }.addOnFailureListener {
                                Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG).show()
                            }
                        }
                        else{
                            Toast.makeText(applicationContext,getString(R.string.this_nick_has_been_taken),Toast.LENGTH_LONG).show()
                        }
                    }
                }



            }else{
                etPassword1.error="Şifreler eşleşmiyor"
                etPassword1.text.clear()
                etPassword2.text.clear()

            }



        }



    }
    fun login(v:View){
        var email=etEmailLogin.text.toString()
        var password=etPasswordLogin.text.toString()
        var empty=false
        if (email.isNullOrBlank()){
            empty=true
            etEmailLogin.error="E-mail kısmı boş geçilemez"
        }
        if (password.isNullOrBlank()){
            empty=true
            etPasswordLogin.error="Şifre kısmı boş geçilemez"
        }



        if (!empty){

            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener {

                Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }
    }

}