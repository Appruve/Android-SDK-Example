# Appruve Android SDK

Example Implementation.

## Setup

1. Add **appruve** maven repository to the **project** ```build.gradle``` file.
```
allprojects {
    repositories {
        ...
        maven {
            url 'https://dl.bintray.com/appruve/maven'
        }
        ...
    }
}
```
2. Add the following to the **module** ```build.gradle``` file:
```
dependencies {
    ...
    implementation 'co.appruve.identitysdk:appruveMobileSDK:0.0.5'
    ...
}
```

## Using

Just start the ```VerificationActivity```:

```
val intent = Intent(this, VerificationActivity::class.java)
val bundle = Bundle()

val customParamsBundle = Bundle()
customParamsBundle.putString("userId", "0caa730e-0f80-4967-b19c-fe9cdbcb80fa")

bundle.putString(APPRUVE_API_TOKEN, "YOUR API TOKEN")
bundle.putBoolean(IS_GHANA_ENABLED, true)
bundle.putBoolean(IS_NIGERIA_ENABLED, true)
bundle.putBoolean(IS_KENYA_ENABLED, true)
bundle.putBundle(CUSTOM_PARAMS, customParamsBundle)

intent.putExtras(bundle)

startActivityForResult(intent, START_VERIFICATION_ACTIVITY_REQUEST)
```
**Note** need to replace ```** YOUR API TOKEN **``` with your **API TOKEN**.

To process the result you need to override ```onActivityResult()``` of your Activity.

```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    // Check which request we're responding to
    if (requestCode == START_VERIFICATION_ACTIVITY_REQUEST) {
        // Make sure the request was successful
        if (resultCode == RESULT_SUCCESS_CODE) {
            if(data != null) {
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
```
