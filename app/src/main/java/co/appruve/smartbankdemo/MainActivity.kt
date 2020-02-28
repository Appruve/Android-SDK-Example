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

            bundle.putString(APPRUVE_API_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmNDk4OGUxMC1hMGU4LTQ4MjUtOTY4MC01ZDVkNzdkMmU1ZDciLCJhdWQiOiIzODdkNzk4Ny1hYTgwLTQ3N2ItOGZjYi1hMzU0OGQyOTkzMTUiLCJzdWIiOiJlN2ZmN2MwZC1hNmJjLTQ0NjAtODNlZi05ZmQxYTA3NmZkZTkiLCJuYmYiOjAsInNjb3BlcyI6WyJ2ZXJpZmljYXRpb25fdmlldyIsInZlcmlmaWNhdGlvbl9saXN0IiwidmVyaWZpY2F0aW9uX2RvY3VtZW50IiwidmVyaWZpY2F0aW9uX2lkZW50aXR5Il0sImV4cCI6MzE1NzY4MjgzMCwiaWF0IjoxNTc5NzU5NjMwfQ.RotgmvvHnCCCDpldWNSz9c6o28H8anHq0IOkD1dJQyI")
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
