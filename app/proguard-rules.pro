# Xposed framework
-keep class de.robv.android.xposed.** { *; }
-keepclassmembers class de.robv.android.xposed.** { *; }

# Our module
-keep class com.indriverbot.** { *; }
-keepclassmembers class com.indriverbot.** { *; }

# Xposed entry point
-keep class * implements de.robv.android.xposed.IXposedHookLoadPackage { *; }
