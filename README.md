# Appruve Android SDK

Example Implementation.

| <img src="https://user-images.githubusercontent.com/5106229/141696501-3dbdca2a-4ef2-41e9-8f97-80d7fdefdad6.jpg" width="250"> | <img src="https://user-images.githubusercontent.com/5106229/141696504-a11670a5-f77c-41d5-aebf-0227bb389c01.jpg" width="250"> | <img src="https://user-images.githubusercontent.com/5106229/141696507-d74cf090-8a30-4b48-bb72-9b75d0bd1683.jpg" width="250"> | <img src="https://user-images.githubusercontent.com/5106229/141696508-b041aaf3-0a3e-4f52-b2ce-a57adc06c592.jpg" width="250"> | <img src="https://user-images.githubusercontent.com/5106229/141696512-6c8d866c-b7fe-40b8-aba7-f923ad741a4c.jpg" width="250"> |
|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|

## Setup

1. Add the JitPack repository to your **project** ```build.gradle``` file.
```groovy
allprojects {
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
        ...
    }
}
```
2. Add the following to the **module** ```build.gradle``` file:
```groovy
dependencies {
    ...
    implementation 'co.appruve:appruve-android-sdk:v0.1.4'
    ...
}
```

Get the latest dependency at [jitpack.io][J].

## Using

Just start the ```VerificationActivity```:

```kotlin
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

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    // Check which request we're responding to
    if (requestCode == START_VERIFICATION_ACTIVITY_REQUEST) {
        // Make sure the request was successful
        if (resultCode == RESULT_SUCCESS_CODE) {
            if(data != null) {
                val isVerified: Boolean = data.extras?.getBoolean(APPRUVE_EXTRA_IS_VERIFIED, false)!!
                val documentType: String = data.extras?.getString(APPRUVE_EXTRA_DOCUMENT_TYPE, "")!!
                val idPhotoUrl = data.extras?.getString(APPRUVE_EXTRA_ID_PHOTO_URL, "")!!
                val selfiePhotoUrl = data.extras?.getString(APPRUVE_EXTRA_SELFIE_PHOTO_URL, "")!!
                val verificationId = data.extras?.getString(APPRUVE_EXTRA_VERIFICATION_ID, "")!!
                val customParams = data.extras?.getString(APPRUVE_EXTRA_CUSTOM_PARAMS, "")!!
                val extractedIDData = data.extras?.getString(APPRUVE_EXTRA_ID_DATA, "")!!

                Log.d(TAG, isVerified.toString())
                Log.d(TAG, documentType)
                Log.d(TAG, idPhotoUrl)
                Log.d(TAG, selfiePhotoUrl)
                Log.d(TAG, verificationId)
                Log.d(TAG, customParams)
                Log.d(TAG, extractedIDData)
            }
        }
    }  else if (resultCode == RESULT_FAILED_CODE) {
        if (data != null) {
            val errorMessage: String =
                data.extras?.getString(APPRUVE_EXTRA_ERROR_DATA, "")!!
            val verificationId =
                data.extras?.getString(APPRUVE_EXTRA_VERIFICATION_ID, "")!!

            Log.d(TAG, errorMessage)
            Log.d(TAG, verificationId)
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
```

## SDK Settings

### ID Capture Only

By default the SDK will take the user through 2 stages of verification.

1. ID Capture
2. Selfie Capture

You can however set the SDK to only do ID Capture. 

```kotlin
bundle.putBoolean(IS_ID_CAPTURE_ONLY, true)
```

The ID Capture process will prompt the user to capture live their ID document, after which OCR will be performed on the document. The OCR process also involves verifying the captured data against the Government ID database. Once the process is complete, the SDK will exit and the captured data will be returned in the `onActivityResult` callback with the `APPRUVE_EXTRA_ID_DATA` bundle key. The data returned with the `APPRUVE_EXTRA_ID_DATA` key is a JSON string. For example:

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    // Check which request we're responding to
    if (requestCode == START_VERIFICATION_ACTIVITY_REQUEST) {
        // Make sure the request was successful
        if (resultCode == RESULT_SUCCESS_CODE) {
            if(data != null) {
                val capturedData: String =
                    data.extras?.getString(APPRUVE_EXTRA_ID_DATA, "")!!
                Log.d(TAG, capturedData)
            }
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
```

### Selfie Capture Only

This setting will only take the user through the selfie capture and liveness test flow.

```kotlin
bundle.putBoolean(IS_SELFIE_CAPTURE_ONLY, true)
```

### Enable/Disable Countries and ID types

By default all three countries (Ghana, Nigeria, Kenya) that we currently support are enabled. However, you can disable specific countries.

**Ghana**
```kotlin
bundle.putBoolean(IS_GHANA_ENABLED, false)
```

**Nigeria**
```kotlin
bundle.putBoolean(IS_NIGERIA_ENABLED, false)
```

**Kenya**
```kotlin
bundle.putBoolean(IS_KENYA_ENABLED, false)
```

You can also disable specific ID types for countries you have enabled.

**Ghana**
```kotlin
bundle.putBoolean(IS_GHANA_VOTER_ID_ENABLED, false)
bundle.putBoolean(IS_GHANA_PASSPORT_ID_ENABLED, false)
bundle.putBoolean(IS_GHANA_DRIVER_LICENSE_ID_ENABLED, false)
bundle.putBoolean(IS_GHANA_SSNIT_ID_ENABLED, false)
```

**Nigeria**
```kotlin
bundle.putBoolean(IS_NIGERIA_VOTER_ID_ENABLED, false)
bundle.putBoolean(IS_NIGERIA_PASSPORT_ID_ENABLED, false)
bundle.putBoolean(IS_NIGERIA_DRIVER_LICENSE_ID_ENABLED, false)
bundle.putBoolean(IS_NIGERIA_NATIONAL_ID_ENABLED, false)
```

**Kenya**
```kotlin
bundle.putBoolean(IS_KENYA_NATIONAL_ID_ENABLED, false)
bundle.putBoolean(IS_KENYA_PASSPORT_ID_ENABLED, false)
```

## Customizing the look

### Material Design theming

Our UI relies on a Material Design theme (ie, a theme that extends from Theme.MaterialComponents). colorPrimary, colorPrimaryDark, and colorAccent.


### Use or extend an SDK theme

The SDK has an out-of-the-box UI that requires a Material Design theme (ie, a style that extends from Theme.MaterialComponents). The SDK respects light, dark, and light with dark action bar themes. The colorPrimary, colorPrimaryDark, and colorAccent attributes are used by the SDK, so it will inherit the material theme from your app. You can extend a SDK theme as follows:

```java
<style name="YourLightTheme" parent="AppruveSdkTheme.Light">
...
</style>
```

You can then set this theme in your AndroidManifest.xml as follows:
```java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.appruve.example" >
    ...
    <application
        ...
        android:theme="@style/YourLightTheme"
        ...
    />
    ...
</manifest>
```

Alternatively, if you don't want to set the theme at the application level, then you will need to do the following:
```java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.appruve.example" >
    ...
    <application ...>
        <activity android:name="co.appruve.identitysdk.VerificationActivity"
            android:theme="@style/YourLightTheme" />

    </application>
    ...
</manifest>
```

### Use your own theme

If you don't extend a SDK theme, you must still apply a theme that extends from Theme.MaterialComponents to the SDK Activity classes.

```java
<style name="YourLightTheme" parent="Theme.MaterialComponents.Light">
    <item name="colorPrimary">@color/my_color_primary</item>
    <item name="colorPrimaryDark">@color/my_color_primary_dark</item>
    <item name="colorAccent">@color/my_color_accent</item>
</style>
```

[J]: https://jitpack.io/#co.appruve/appruve-android-sdk
