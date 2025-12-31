# Xposed
-keep class de.robv.android.xposed.** { *; }
-keepclassmembers class de.robv.android.xposed.** { *; }

# Keep our hooks
-keep class com.indriverbot.** { *; }
-keepclassmembers class com.indriverbot.** { *; }

# Keep Xposed entry point
-keep class * implements de.robv.android.xposed.IXposedHookLoadPackage { *; }
-keepclasseswithmembers class * {
    public void handleLoadPackage(de.robv.android.xposed.callbacks.XC_LoadPackage$LoadPackageParam);
}
