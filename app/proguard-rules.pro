# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\DATDB\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#loại bỏ các tập tin xử lý thừa
-dontpreverify
#Chỉ định để đóng gói lại tất cả các tập tin lớp được đổi tên,
-repackageclasses ''
#Chỉ định truy cập của các lớp và các thành viên lớp có thể được mở rộng trong khi biên dịch
-allowaccessmodification
#Chỉ định tối ưu hóa
-optimizations !code/simplification/arithmetic
-keepattributes *Annotation*
#Giữ lại class
-keep class com.lib.jsdk.sdk.JSdk { *; }
-keep class com.lib.jsdk.callback.OnRegisterListner { *; }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#Các class được bảo mật
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}