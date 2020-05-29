package com.hari.smack.Controller

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.hari.smack.R
import com.hari.smack.Services.AuthService
import com.hari.smack.Services.UserDataService
import com.hari.smack.Utilities.BROADCAST_USER_DATA_CHANGE
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
        val userName= createUserNameText.text.toString()
        val email= createEmailText.text.toString()
        val password= createPasswordText.text.toString()
        if (userName.isNotEmpty()&& email.isNotEmpty()&&password.isNotEmpty()){
            AuthService.registerUser(this,email,password){registerSucess->

                if(registerSucess){
                    AuthService.LoginUser(this,email,password){loginSucess->
                        if(loginSucess){
                            AuthService.createUser(this,userName,email,userAvatar,avatarColor){createSuccess->
                                if(createSuccess){
                                    val UserDatatChange= Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(UserDatatChange)
                                    enableSpinner(false)
                                    finish()


                                }else errorToast()

                            }
                        }else errorToast()

                    }
                }else errorToast()

            }
        }
        else{
            Toast.makeText(this,"Make Sure Every thing is filled!!",Toast.LENGTH_LONG).show()
            enableSpinner(false)

        }



    }
    fun errorToast(){
        Toast.makeText(this,"Something went wrong try again later!!",Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }
    fun enableSpinner(enable:Boolean){
        if(enable){
            spinnerProgress.setBackgroundColor(Color.BLUE)
        }
        else{
            spinnerProgress.setBackgroundColor(Color.TRANSPARENT)
        }
        createUserBtn.isEnabled=!enable
        createAvatarImageView.isEnabled= !enable
        backgroundColorBtn.isEnabled= !enable
    }
}
