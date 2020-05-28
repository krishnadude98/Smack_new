package com.hari.smack.Services

import android.app.DownloadManager
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.hari.smack.Utilities.LOGIN_URL

import com.hari.smack.Utilities.REGISTER_URL
import com.hari.smack.Utilities.URL_CREATE_LOGIN
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Method

object AuthService {
    var isLoggedIn= true
    var authToken=""
    var userEmail=""
    fun registerUser(
        context: Context,
        email: String,
        password: String,
        complete: (Boolean) -> Unit
    ) {

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()
        val registerRequest =
            object : StringRequest(Method.POST, REGISTER_URL, Response.Listener { response ->
                println(response)
                complete(true)
            }, Response.ErrorListener { err ->
                Log.d("Error", "could not register user : $err")
                Log.d("founder","error in registeration process")
                complete(false)
            }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset= utf-8"
                }

                override fun getBody(): ByteArray {
                    return requestBody.toByteArray()
                }
            }
        Volley.newRequestQueue(context).add(registerRequest)


    }

    fun LoginUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {
        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()
        val loginRequest =
            object : JsonObjectRequest(Method.POST, LOGIN_URL, null, Response.Listener { response ->
                try{
                    userEmail= response.getString("user")
                    authToken = response.getString("token")
                    isLoggedIn= true

                }catch (e:JSONException){
                    Log.d("JSON","EXC:"+e.localizedMessage)
                    complete(false)
                }


                complete(true)
            }, Response.ErrorListener { err ->
                Log.d("Error", "could not login user : $err")
                complete(false)

            }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset= utf-8"
                }

                override fun getBody(): ByteArray {
                    return requestBody.toByteArray()

                }
            }
        Volley.newRequestQueue(context).add(loginRequest)
    }
    fun createUser(context:Context,name:String,email:String,avatarName:String,avatarColor:String,complete: (Boolean) -> Unit){
        val jsonBody= JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("email", email)
        jsonBody.put("avatarName",avatarName)
        jsonBody.put("avatarColor",avatarColor)
        val requestBody= jsonBody.toString()
        val createRequest =
            object : JsonObjectRequest(Method.POST, URL_CREATE_LOGIN, null, Response.Listener { response ->
                try{
                    UserDataService.name= response.getString("name")
                    UserDataService.email=response.getString("email")
                    UserDataService.avatarName=response.getString("avatarName")
                    UserDataService.avatarColor= response.getString("avatarColor")
                    UserDataService.id= response.getString("_id")
                    complete(true)

                }catch (e:JSONException){
                    Log.d("JSON","EXEC "+e.localizedMessage)
                    Log.d("Errror in create User","ERROR IN NEW")
                    complete(false)
                }


                complete(true)
            }, Response.ErrorListener { err ->
                Log.d("Error", "could not add user : $err")
                complete(false)

            }){

                override fun getBodyContentType(): String {
                    return "application/json; charset= utf-8"
                }

                override fun getBody(): ByteArray {
                    return requestBody.toByteArray()

                }

                override fun getHeaders(): MutableMap<String, String> {
                    val headers= HashMap<String,String>()
                    headers.put("Authorization","Bearer $authToken")
                    return headers
                }

            }
        Volley.newRequestQueue(context).add(createRequest)
    }


}


