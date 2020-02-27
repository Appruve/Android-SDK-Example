package co.appruve.smartbankdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import co.appruve.identitysdk.*
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var button: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        button.setOnClickListener {
            val intent = Intent(this, AppruveActivity::class.java)
            val bundle = Bundle()

            bundle.putString(APPRUVE_API_TOKEN, "YOUR API TOKEN")
            bundle.putString(STATUS_BAR_COLOR, "#00693C")
            bundle.putString(TOOLBAR_BACKGROUND_COLOR, "#0F5738")
            bundle.putString(NAVIGATION_BUTTON_BACKGROUND_COLOR, "#75AF96")
            bundle.putString(ICON_COLOR, "#75AF96")
            bundle.putString(ICON_TEXT_COLOR, "#292f33")
            bundle.putString(NORMAL_TEXT_COLOR, "#292f33")
            bundle.putString(HEADER_COLOR, "#292f33")
            bundle.putString(SUB_HEADER_COLOR, "#292f33")
            bundle.putString(TEXT_LABEL_COLOR, "#00693C")
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
                if(data != null) {
                    val verified = data.extras?.getBoolean(APPRUVE_EXTRA_IS_VERIFIED)
                    Log.e(TAG, verified?.toString() ?: "NONE")
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
