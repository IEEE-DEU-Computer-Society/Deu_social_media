package com.example.deu_social_media

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.deu_social_media.databinding.ActivityLoginBinding
import com.example.deu_social_media.databinding.FragmentLoginBinding
import com.example.deu_social_media.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    lateinit var bindingRegister : FragmentRegisterBinding
    lateinit var bindingLogin: FragmentLoginBinding

    //ELİF ARAS Authentication için firebase bağlantısı
    var mAuth: FirebaseAuth? = null;
    //ELİF ARAS student için firestore bağlantısı
    var firebaseFirestoreDb: FirebaseFirestore? = null;
    var collectionReference: CollectionReference? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        bindingRegister= FragmentRegisterBinding.inflate(layoutInflater)
        bindingLogin= FragmentLoginBinding.inflate(layoutInflater)
        binding= ActivityLoginBinding.inflate(layoutInflater)


        mAuth = FirebaseAuth.getInstance();
        //ELİF ARAS student için firestore collection bağlantısı
        collectionReference = firebaseFirestoreDb?.collection("students");

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
    fun goToregister(v:View){
        var action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        Navigation.findNavController(v).navigate(action)
    }
    fun register(v:View){
        var nick=bindingRegister.etNick.text.toString()
        var email=bindingRegister.etEmail.text.toString()
        var password1=bindingRegister.etPassword1.text.toString()
        var password2=bindingRegister.etPassword2.text.toString()
        var empty=false
        if (nick.isNullOrBlank()){
            empty=true
            bindingRegister.etNick.error="Nick kısmı boş geçilemez"
        }
        if(email.isNullOrBlank()) {
            empty = true
            bindingRegister.etEmail.error="E-mail kısmı boş geçilemez"

        }
        if(password1.isNullOrBlank()){
            empty=true
            bindingRegister.etPassword1.error="Şifre kısmı boş geçilemez"
        }
        if(password2.isNullOrBlank()){
            empty=true
            bindingRegister.etPassword2.error="Şifre kısmı boş geçilemez"
        }



        if (!empty){
            if(password1.equals(password2)){
                mAuth!!.createUserWithEmailAndPassword(email,password1).addOnCompleteListener {
                    if (it.isSuccessful){
                        val action=RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        Navigation.findNavController(v).navigate(action)

                    }
                }.addOnFailureListener {
                    Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG)
                }





            }else{
                bindingRegister.etPassword1.error="Şifreler eşleşmiyor"
                bindingRegister.etPassword1.text.clear()
                bindingRegister.etPassword2.text.clear()

            }



        }



    }
    fun login(v:View){
        var email=bindingLogin.etEmailLogin.text.toString()
        var password=bindingLogin.etPasswordLogin.text.toString()
        var empty=false
        if (email.isNullOrBlank()){
            empty=true
            bindingLogin.etEmailLogin.error="E-mail kısmı boş geçilemez"
        }
        if (password.isNullOrBlank()){
            empty=true
            bindingLogin.etPasswordLogin.error="Şifre kısmı boş geçilemez"
        }



        if (!empty){

            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener {

                Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG)
            }

        }
    }

}