package co.appruve.smartbankdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import co.appruve.identitysdk.*
import com.google.android.material.button.MaterialButton

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var button: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        button.setOnClickListener {
            val intent = Intent(this, VerificationActivity::class.java)
            val bundle = Bundle()

            bundle.putString(APPRUVE_API_TOKEN, "YOUR API TOKEN")
            bundle.putBoolean(IS_GHANA_ENABLED, true)
            bundle.putBoolean(IS_NIGERIA_ENABLED, true)
            bundle.putBoolean(IS_KENYA_ENABLED, true)

            intent.putExtras(bundle)

            startActivityForResult(intent, START_VERIFICATION_ACTIVITY_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        if (requestCode == START_VERIFICATION_ACTIVITY_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_SUCCESS_CODE) {
                if (data != null) {
                    val isVerified: Boolean =
                        data.extras?.getBoolean(APPRUVE_EXTRA_IS_VERIFIED, false)!!
                    val documentType: String =
                        data.extras?.getString(APPRUVE_EXTRA_DOCUMENT_TYPE, "")!!
                    val idPhotoUrl = data.extras?.getString(APPRUVE_EXTRA_ID_PHOTO_URL, "")!!
                    val selfiePhotoUrl =
                        data.extras?.getString(APPRUVE_EXTRA_SELFIE_PHOTO_URL, "")!!
                    val verificationId =
                        data.extras?.getString(APPRUVE_EXTRA_VERIFICATION_ID, "")!!
                    Log.e(TAG, isVerified.toString())
                    Log.d(TAG, documentType)
                    Log.d(TAG, idPhotoUrl)
                    Log.d(TAG, selfiePhotoUrl)
                    Log.d(TAG, verificationId)
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val START_VERIFICATION_ACTIVITY_REQUEST = 1
    }
}
