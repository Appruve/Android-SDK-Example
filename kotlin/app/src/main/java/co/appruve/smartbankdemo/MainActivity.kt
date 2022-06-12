package co.appruve.smartbankdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import co.appruve.identitysdk.*
import co.appruve.identitysdk.Constants.APPRUVE_API_TOKEN
import co.appruve.identitysdk.Constants.APPRUVE_EXTRA_CUSTOM_PARAMS
import co.appruve.identitysdk.Constants.APPRUVE_EXTRA_DOCUMENT_TYPE
import co.appruve.identitysdk.Constants.APPRUVE_EXTRA_ERROR_DATA
import co.appruve.identitysdk.Constants.APPRUVE_EXTRA_ID_DATA
import co.appruve.identitysdk.Constants.APPRUVE_EXTRA_ID_PHOTO_URL
import co.appruve.identitysdk.Constants.APPRUVE_EXTRA_IS_VERIFIED
import co.appruve.identitysdk.Constants.APPRUVE_EXTRA_SELFIE_PHOTO_URL
import co.appruve.identitysdk.Constants.APPRUVE_EXTRA_VERIFICATION_ID
import co.appruve.identitysdk.Constants.CUSTOM_PARAMS
import co.appruve.identitysdk.Constants.IS_GHANA_DRIVER_LICENSE_ID_ENABLED
import co.appruve.identitysdk.Constants.IS_GHANA_ENABLED
import co.appruve.identitysdk.Constants.IS_GHANA_PASSPORT_ID_ENABLED
import co.appruve.identitysdk.Constants.IS_GHANA_SSNIT_ID_ENABLED
import co.appruve.identitysdk.Constants.IS_GHANA_VOTER_ID_ENABLED
import co.appruve.identitysdk.Constants.IS_ID_CAPTURE_ONLY
import co.appruve.identitysdk.Constants.IS_KENYA_ENABLED
import co.appruve.identitysdk.Constants.IS_KENYA_NATIONAL_ID_ENABLED
import co.appruve.identitysdk.Constants.IS_KENYA_PASSPORT_ID_ENABLED
import co.appruve.identitysdk.Constants.IS_NIGERIA_DRIVER_LICENSE_ID_ENABLED
import co.appruve.identitysdk.Constants.IS_NIGERIA_ENABLED
import co.appruve.identitysdk.Constants.IS_NIGERIA_NATIONAL_ID_ENABLED
import co.appruve.identitysdk.Constants.IS_NIGERIA_PASSPORT_ID_ENABLED
import co.appruve.identitysdk.Constants.IS_NIGERIA_VOTER_ID_ENABLED
import co.appruve.identitysdk.Constants.IS_RETRY_ENABLED
import co.appruve.identitysdk.Constants.IS_SELFIE_CAPTURE_ONLY
import co.appruve.identitysdk.Constants.RESULT_FAILED_CODE
import co.appruve.identitysdk.Constants.RESULT_SUCCESS_CODE
import co.appruve.identitysdk.Constants.TRANSACTION_REFERENCE
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var button: MaterialButton
    private lateinit var kycActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        kycActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e(TAG, result.toString())
            val data = result.data
            if (result.resultCode == RESULT_SUCCESS_CODE) {
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
                    val customParams =
                        data.extras?.getString(APPRUVE_EXTRA_CUSTOM_PARAMS, "")!!
                    val extractedIDData =
                        data.extras?.getString(APPRUVE_EXTRA_ID_DATA, "")!!


                    Log.d(TAG, isVerified.toString())
                    Log.d(TAG, documentType)
                    Log.d(TAG, idPhotoUrl)
                    Log.d(TAG, selfiePhotoUrl)
                    Log.d(TAG, verificationId)
                    Log.d(TAG, customParams)
                    Log.d(TAG, extractedIDData)
                }
            } else if (result.resultCode == RESULT_FAILED_CODE) {
                if (data != null) {
                    val errorMessage: String =
                        data.extras?.getString(APPRUVE_EXTRA_ERROR_DATA, "")!!
                    val verificationId =
                        data.extras?.getString(APPRUVE_EXTRA_VERIFICATION_ID, "")!!

                    Log.d(TAG, errorMessage)
                    Log.d(TAG, verificationId)
                }
            }
        }

        button.setOnClickListener {
            val intent = Intent(this, VerificationActivity::class.java)
            val bundle = Bundle()

            bundle.putString(APPRUVE_API_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5YzkzNzU5Yi02NjMyLTRlOTYtOTM5My00ZDU0NjJlYTMxNmMiLCJhdWQiOiI4MmU4MWJlYy1mMDExLTQyMTgtOTVjZi1hYTkxOTE1NTA3YTgiLCJzdWIiOiJjOWI3OGNiMS05MzA4LTQ4ZjgtYTE5NC0xNzVkN2VkMDllNjkiLCJuYmYiOjAsInNjb3BlcyI6WyJ2ZXJpZmljYXRpb25fdmlldyIsInZlcmlmaWNhdGlvbl9saXN0IiwidmVyaWZpY2F0aW9uX2RvY3VtZW50IiwidmVyaWZpY2F0aW9uX2lkZW50aXR5Il0sImV4cCI6MzE4NzQyMTE1MywiaWF0IjoxNjA5NTg0MzUzfQ.f-XbdiwSehmmXYN51l4H-hAW4EDQvobsaBuCpgtnh04")
            bundle.putBoolean(IS_RETRY_ENABLED, false) // Disable retry on OCR failure
            bundle.putBoolean(IS_ID_CAPTURE_ONLY, false) // Capture only ID Document
            bundle.putBoolean(IS_SELFIE_CAPTURE_ONLY, false) // Capture only Selfie
            // bundle.putString(TRANSACTION_REFERENCE, "7216eed5-eda0-475e-86fe-07b37a95826c") // Set transaction ref previously returned for re-enrollment

            // enable or disable countries
            bundle.putBoolean(IS_GHANA_ENABLED, true)
            bundle.putBoolean(IS_NIGERIA_ENABLED, true)
            bundle.putBoolean(IS_KENYA_ENABLED, true)

            // enable or disable Ghana IDs
            bundle.putBoolean(IS_GHANA_VOTER_ID_ENABLED, true)
            bundle.putBoolean(IS_GHANA_PASSPORT_ID_ENABLED, true)
            bundle.putBoolean(IS_GHANA_DRIVER_LICENSE_ID_ENABLED, true)
            bundle.putBoolean(IS_GHANA_SSNIT_ID_ENABLED, true)

            // enable or disable Nigeria IDs
            bundle.putBoolean(IS_NIGERIA_VOTER_ID_ENABLED, true)
            bundle.putBoolean(IS_NIGERIA_PASSPORT_ID_ENABLED, true)
            bundle.putBoolean(IS_NIGERIA_DRIVER_LICENSE_ID_ENABLED, true)
            bundle.putBoolean(IS_NIGERIA_NATIONAL_ID_ENABLED, true)

            // enable or disable Kenya IDs
            bundle.putBoolean(IS_KENYA_NATIONAL_ID_ENABLED, true)
            bundle.putBoolean(IS_KENYA_PASSPORT_ID_ENABLED, true)

            // Attach custom params against the verification record
            val customParamsBundle = Bundle()
            customParamsBundle.putString("userId", "a00d2f6b-0af1-4fdd-893c-6d392d1b1176")
            bundle.putBundle(CUSTOM_PARAMS, customParamsBundle)

            intent.putExtras(bundle)
            kycActivityLauncher.launch(intent)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
