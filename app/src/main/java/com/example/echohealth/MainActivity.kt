package com.example.echohealth
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.echohealth.ui.theme.ECHOHEALTHTheme
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Color

//import com.example.echohealth.RecordAudioPermissionTextProvider

class MainActivity : ComponentActivity() {

    private val recordpermission = Manifest.permission.RECORD_AUDIO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ECHOHEALTHTheme {


                val permissionviewModel = viewModel<PermissionviewModelViewModel>()
                val permissiondialog = permissionviewModel.visualPermissionDialog
                val recordPermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = {  it->
                        permissionviewModel.onAnswer(
                            permission = Manifest.permission.RECORD_AUDIO,
                            isGiven = it
                        )
                    }
                )


                when (val permission = permissiondialog.value) {
                    null -> {
                        Log.d("inmain", "Permission has  null val")
                    }

                    else -> {
                        PermissionBox(
                            textextractor = TextProvider(),
                            ClickOk  = {
                            permissionviewModel.declineDialog()
                            recordPermissionResultLauncher.launch(permission)
                        },
                            DeniedPermanently= !shouldShowRequestPermissionRationale(permission),
                            onDeny = permissionviewModel::declineDialog,

                        ClickSettings = ::openAppSettings
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.Black),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        recordPermissionResultLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }) {
                        Text(text = "Request Permission to record audio")
                    }
                }
            }
            }
        }
    }
fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}