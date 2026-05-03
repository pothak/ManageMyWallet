# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.util.**
-dontwarn org.**

# SQLCipher
-keep class net.sqlcipher.** { *; }
-keep class net.zetetic.** { *; }
-keep class androidx.sqlite.** { *; }
-dontwarn net.sqlcipher.**
-dontwarn org.**

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
