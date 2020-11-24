package com.example.tugoflogic.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel


public class MainClaim(application: Application) : AndroidViewModel(application)  {

    var _id :  Int
    var game_id : Int
    var user_id : Int
    var statement: String

    init {
        _id = 0
        game_id = 0
        user_id = 0
        statement = ""
    }

}