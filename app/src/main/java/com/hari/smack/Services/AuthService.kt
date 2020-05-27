package com.hari.smack.Services

import android.app.DownloadManager
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.hari.smack.Utilities.REGISTER_URL
import org.json.JSONObject
object AuthService{
    fun registerUser(context: Context,email:String,password:String,complete:(Boolean)->Unit){
        val jsonBody= JSONObject()
        jsonBody.put("email",email)
        jsonBody.put("password",password)
        val requestBody= jsonBody.toString()
        val registerRequest= object:StringRequest(Method.POST, REGISTER_URL,Response.Listener { response-> println(response)
        complete(true)},Response.ErrorListener {err->
            Log.d("Error","could not register user : $err")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset= utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()            }
        }
        Volley.newRequestQueue(context).add(registerRequest)


    }
  }