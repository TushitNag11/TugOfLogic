package com.example.tugoflogic.Admin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.MainClaimService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.models.ESocket
import com.example.tugoflogic.models.MainClaim
import kotlinx.android.synthetic.main.activity_m_c_admin.*
import kotlinx.android.synthetic.main.activity_main_claim.*


class MCAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_c_admin)

        val sharedPref: SharedPreferences =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)
        val gameID = sharedPref.getInt("GAME_ID", 0).toString().toInt();
        println("Game ID ${gameID}")
        tvGameId.setText(gameID.toString())

        var mainClaimService = MainClaimService(this)


        mcSubmitBtn.setOnClickListener(View.OnClickListener {

            mainClaimService.findAll()
//            fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
//                observe(lifecycleOwner, object : Observer<T> {
//                    override fun onChanged(t: T?) {
//                        observer.onChanged(t)
//                        removeObserver(this)
//                    }
//                })
//            }

        })

        mainClaimService.listLiveData.observe(this, Observer<List<MainClaim>> {

            var mainClaimID = it.size + 1
            //checking whether main claim is empty or not
//                if() the main claim is empty or not

            var mainClaimStatement = tvMainClaim.getText().toString()


            if (mainClaimStatement != "") {
                mainClaimService.create(mainClaimID, gameID, mainClaimStatement)

            } else {
                Toast.makeText(
                    applicationContext,
                    "Main Claim should not be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }

            println(mainClaimID.toString());
        })



        mainClaimService.newMainClaimData.observe(this, Observer { ms ->
            ms?.let {
                if (it > 0) {
                    println("New Mainclaim: " + it);
                    // new mainclaim is created
                    // call ws to broadcast to users
                    var socketService = SocketService(this);
                    socketService.sendMessage(ESocket.SHOW_MAINCLAIM.value + "|" + it)
                }
            }
        })
    }
}