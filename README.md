# Appruve Android SDK

Example Implementation.

## Setup

1. Add the JitPack repository to your **project** ```build.gradle``` file.
```
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
```
dependencies {
    ...
    implementation 'co.appruve:appruve-android-sdk:v0.0.5'
    ...
}
```

Get the latest dependency at [jitpack.io][J].

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
