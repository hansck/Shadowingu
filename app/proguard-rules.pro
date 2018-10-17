# Shadowingu ProGuard rules
-dontskipnonpubliclibraryclasses
-forceprocessing
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification

# Suppress warnings
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn android.arch.util.paging.CountedDataSource
-dontwarn android.arch.persistence.room.paging.LimitOffsetDataSource
-dontwarn com.google.android.gms.**

# Preserve & do not minify this classes & libraries
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep class * extends android.app.Activity
-keep class com.hansck.shadowingu.model.** { *; }
-keep public class com.google.firebase.database.** { *; }
-keep public class com.google.android.gms.* { public *; }

-keepclassmembers enum * { *; }

-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes Exceptions