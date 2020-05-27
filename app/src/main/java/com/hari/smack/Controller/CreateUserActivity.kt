package com.hari.smack.Controller

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hari.smack.R
import com.hari.smack.Services.AuthService
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlin.random.Random

class CreateUserActivity : AppCompatActivity() {

    var userAvatar= "profileDefault"
    var avatarColor="[0.5,0.5,0.5,1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }
    fun generateUserAvatar(view: View){
        val random = Random
        val color = random.nextInt(2)
        val avatar= random.nextInt(28)
        if(color==0){
            userAvatar= "light$avatar"
        }
        else{
            userAvatar= "dark$avatar"
        }
        val resourceId= resources.getIdentifier(userAvatar,"drawable",packageName)
        createAvatarImageView.setImageResource(resourceId)

    }
    fun generateColorClicked(view: View){
        val random = Random
        val r= random.nextInt(255)
        val g= random.nextInt(255)
        val b = random.nextInt(255)
        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))
        val savedR= r.toDouble()/255
        val savedg= g.toDouble()/255
        val savedb=b.toDouble()/255
        avatarColor= "[$savedR,$savedg,$savedb,1]"


    }
    fun createUserBtnClicked(view: View){
        val email= createEmailText.text.toString()
        val password= createPasswordText.text.toString()
        AuthService.registerUser(this,email,password){registerSucess->
            if(registerSucess){
                AuthService.LoginUser(this,email,password){loginSucess->
                    if(loginSucess){
                        println(AuthService.authToken)
                        println(AuthService.userEmail)
                    }

                }
            }

        }


    }
}
