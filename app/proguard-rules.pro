# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/liangzili/Library/Android/sdk/tools/proguard/proguard-android.txt
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

# 友盟避免混淆
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**

-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**

-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**

-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

-keep public class **.R$*{
    public static final int *;
    }

-keepclassmembers class **.R$* {
       public static <fields>;
}

#libray中swipeback、http、GsonUtils都不能混淆，里面包含Gson解析相关的类
-keep class me.imid.swipebacklayout.lib.app.** {*;}
-keep class com.jiayantech.library.http.** {*;}
-keep class com.jiayantech.library.utils.GsonUtils.**

#项目中用到的不能混淆的库
-dontwarn com.squareup.picasso.**
-keep class com.squareup.okhttp.** {*;}
-keep class com.jiayantech.jyandroid.model.** {*;}
-keep class com.jiayantech.jyandroid.base.BaseModel

#Gson解析不能混淆的类
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

#不能混淆EventBus接收事件的方法onEvent
-keepclassmembers class ** {
    public void onEvent(**);
}

####### RxAndroid #######
-dontwarn rx.internal.util.unsafe.**
