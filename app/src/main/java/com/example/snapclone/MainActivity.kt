package com.example.snapclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    var EmailEditText: EditText? = null
    var PasswordEditText: EditText?=null
    val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EmailEditText=findViewById(R.id.EmailEditText)
        PasswordEditText = findViewById(R.id.PasswordEditText)
        if(mAuth.currentUser !=null){
            logIn()
        }
    }
    fun goClicked(view: View){
        //check if we can login the user
        mAuth.signInWithEmailAndPassword(EmailEditText?.text.toString(), PasswordEditText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    logIn()
                } else {
                    //sign up the user
                    mAuth.createUserWithEmailAndPassword(EmailEditText?.text.toString(),PasswordEditText?.text.toString()).addOnCompleteListener( this){task ->
                        if(task.isSuccessful){
                            //add to database
                            FirebaseDatabase.getInstance().getReference().child("Users").child(task.result?.user!!.uid).child("email").setValue(EmailEditText?.text.toString())
                            logIn()
                        }else{
                            Toast.makeText(this,"LogIn Failed try again",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

        //sign up the user
    }

    fun logIn(){
        //move to next activity
        val intent = Intent(this,SnapActivity::class.java)
        startActivity(intent)
    }
}
