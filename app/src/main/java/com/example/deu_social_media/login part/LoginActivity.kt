package com.example.deu_social_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.deu_social_media.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
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

}