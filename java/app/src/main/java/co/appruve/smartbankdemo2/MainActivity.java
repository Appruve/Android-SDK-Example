package co.appruve.smartbankdemo2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import co.appruve.identitysdk.VerificationActivity;
import co.appruve.identitysdk.Constants;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    private ActivityResultLauncher<Intent> kycActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kycActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    if (result.getResultCode() == Constants.RESULT_SUCCESS_CODE) {
                        if (data != null) {
                            boolean isVerified = data.getBooleanExtra(Constants.APPRUVE_EXTRA_IS_VERIFIED, false);
                            String documentType = data.getStringExtra(Constants.APPRUVE_EXTRA_DOCUMENT_TYPE);
                            String idPhotoUrl = data.getStringExtra(Constants.APPRUVE_EXTRA_ID_PHOTO_URL);
                            String selfiePhotoUrl = data.getStringExtra(Constants.APPRUVE_EXTRA_ID_PHOTO_URL);
                            String verificationId = data.getStringExtra(Constants.APPRUVE_EXTRA_VERIFICATION_ID);
                            String customParams = data.getStringExtra(Constants.APPRUVE_EXTRA_ID_DATA);
                            String extractedIDData = data.getStringExtra(Constants.APPRUVE_EXTRA_CUSTOM_PARAMS);

                            Log.d(TAG, Boolean.toString(isVerified));
                            Log.d(TAG, documentType == null ? "" : documentType);
                            Log.d(TAG, idPhotoUrl == null ? "" : idPhotoUrl);
                            Log.d(TAG, selfiePhotoUrl == null ? "" : selfiePhotoUrl);
                            Log.d(TAG, verificationId == null ? "" : verificationId);
                            Log.d(TAG, customParams == null ? "" : customParams);
                            Log.d(TAG, extractedIDData == null ? "" : extractedIDData);
                        }
                    }  else if (result.getResultCode() == Constants.RESULT_FAILED_CODE) {
                        if (data != null) {
                            String errorMessage = data.getStringExtra(Constants.APPRUVE_EXTRA_ERROR_DATA);
                            String verificationId = data.getStringExtra(Constants.APPRUVE_EXTRA_VERIFICATION_ID);
                            Log.d(TAG, errorMessage == null ? "" : errorMessage);
                            Log.d(TAG, verificationId == null ? "" : verificationId);
                        }
                    }
                });

        MaterialButton button = (MaterialButton) findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString(Constants.APPRUVE_API_TOKEN, "YOUR_API_TOKEN");
            bundle.putBoolean(Constants.IS_RETRY_ENABLED, true); // Disable retry on OCR failure
            bundle.putBoolean(Constants.IS_ID_CAPTURE_ONLY, false); // Capture only ID Document
            bundle.putBoolean(Constants.IS_SELFIE_CAPTURE_ONLY, false); // Capture only Selfie
            bundle.putString(Constants.TRANSACTION_REFERENCE, "3e639285-22ee-45a3-beb2-43e320d391a1"); // Set transaction ref previously returned for re-enrollment

            // enable or disable countries
            bundle.putBoolean(Constants.IS_GHANA_ENABLED, true);
            bundle.putBoolean(Constants.IS_NIGERIA_ENABLED, true);
            bundle.putBoolean(Constants.IS_KENYA_ENABLED, true);

            // enable or disable Ghana IDs
            bundle.putBoolean(Constants.IS_GHANA_VOTER_ID_ENABLED, true);
            bundle.putBoolean(Constants.IS_GHANA_PASSPORT_ID_ENABLED, true);
            bundle.putBoolean(Constants.IS_GHANA_DRIVER_LICENSE_ID_ENABLED, true);
            bundle.putBoolean(Constants.IS_GHANA_SSNIT_ID_ENABLED, true);

            // enable or disable Nigeria IDs
            bundle.putBoolean(Constants.IS_NIGERIA_VOTER_ID_ENABLED, true);
            bundle.putBoolean(Constants.IS_NIGERIA_PASSPORT_ID_ENABLED, true);
            bundle.putBoolean(Constants.IS_NIGERIA_DRIVER_LICENSE_ID_ENABLED, true);
            bundle.putBoolean(Constants.IS_NIGERIA_NATIONAL_ID_ENABLED, true);

            // enable or disable Kenya IDs
            bundle.putBoolean(Constants.IS_KENYA_NATIONAL_ID_ENABLED, true);
            bundle.putBoolean(Constants.IS_KENYA_PASSPORT_ID_ENABLED, true);

            // Attach custom params against the verification record
            Bundle customParamsBundle = new Bundle();
            customParamsBundle.putString("userId", "a00d2f6b-0af1-4fdd-893c-6d392d1b1176");
            bundle.putBundle(Constants.CUSTOM_PARAMS, customParamsBundle);

            intent.putExtras(bundle);
            kycActivityLauncher.launch(intent);
        });
    }
}