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

            val customParamsBundle = Bundle()
            customParamsBundle.putString("userId", "0caa730e-0f80-4967-b19c-fe9cdbcb80fa")

            bundle.putString(APPRUVE_API_TOKEN, "YOUR API TOKEN")
            bundle.putBoolean(IS_ID_CAPTURE_ONLY, false) // Capture only ID Document
            bundle.putBoolean(IS_SELFIE_CAPTURE_ONLY, true) // Capture only Selfie
            // Set transaction ref previously returned to re-enroll or continue with same verification
            bundle.putString(TRANSACTION_REFERENCE, "b1543d5d-fc98-41ac-a9fa-ff9be2a159b6")

            bundle.putBoolean(IS_GHANA_ENABLED, true)
            bundle.putBoolean(IS_NIGERIA_ENABLED, true)
            bundle.putBoolean(IS_KENYA_ENABLED, true)
            bundle.putBoolean(IS_NIGERIA_VOTER_ID_ENABLED, true)
            bundle.putBoolean(IS_NIGERIA_PASSPORT_ID_ENABLED, false)
            bundle.putBoolean(IS_NIGERIA_DRIVER_LICENSE_ID_ENABLED, false)
            bundle.putBoolean(IS_NIGERIA_NATIONAL_ID_ENABLED, false)
            bundle.putBundle(CUSTOM_PARAMS, customParamsBundle)

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
