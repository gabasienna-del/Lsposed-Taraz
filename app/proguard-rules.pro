# Xposed framework classes
-keep class de.robv.android.xposed.** { *; }
-keepclassmembers class de.robv.android.xposed.** { *; }

# Our module classes
-keep class com.indriverbot.** { *; }
-keepclassmembers class com.indriverbot.** { *; }

# Xposed entry points
-keep class * implements de.robv.android.xposed.IXposedHookLoadPackage { *; }
-keepclasseswithmembers class * {
    public void handleLoadPackage(de.robv.android.xposed.callbacks.XC_LoadPackage$LoadPackageParam);
}
