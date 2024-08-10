package com.example.echohealth
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


    class PermissionviewModelViewModel : ViewModel() {
        var visualPermissionDialog= mutableStateOf<String?>(null)
        fun onAnswer(
            permission: String,
            isGiven: Boolean
        ) {
            if (!isGiven){

                visualPermissionDialog.value=permission
            }
        }
        fun declineDialog() {
            visualPermissionDialog.value=null
        }

    }

