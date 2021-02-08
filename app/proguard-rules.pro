# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Nirali\3.Software\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

-dontwarn org.xmlpull.v1.**

-dontwarn okio.**
-dontwarn com.squareup.okhttp3.**

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp3.** { *; }
-dontwarn okhttp3.internal.platform.*

-keep interface com.squareup.okhttp3.** { *; }
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

-dontwarn org.kobjects.**
-dontwarn org.ksoap2.**
-dontwarn org.kxml2.**
-dontwarn org.xmlpull.v1.**

-keep class org.kobjects.** { *; }
-keep class org.ksoap2.** { *; }
-keep class org.kxml2.** { *; }
-keep class org.xmlpull.** { *; }


# for okhttp3
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn org.conscrypt.Conscrypt
-dontwarn okhttp3.internal.platform.*


#for joda time
-dontwarn org.joda.convert.FromString
-dontwarn org.joda.convert.ToString

#for retrofit
-dontwarn kotlin.Unit
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.concurrent.GuardedBy
-dontwarn javax.annotation.Nonnull
-dontwarn org.jetbrains.annotations.NotNull
-dontwarn kotlin.Metadata
-dontwarn kotlin.jvm.JvmName
-dontwarn javax.annotation.meta.TypeQualifierDefault
-dontwarn kotlin.jvm.internal.Intrinsics

-keep class org.jetbrains.annotations.** { *; }
-dontwarn org.jetbrains.annotations.**

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontoptimize
-dontpreverify

##################################


-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# Begin twitter 4j.
-dontwarn twitter4j.**
-keep  class twitter4j.conf.PropertyConfigurationFactory
-keep class twitter4j.** { *; }
# End twitter 4j.


# Start androidsocialnetworks
-keep class com.androidsocialnetworks.lib.** { *; }
# End androidsocialnetworks.

# Google api
-keep class com.google.** { *;}
-keep interface com.google.** { *;}
-dontwarn com.google.**

-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue
-keepattributes *Annotation*,Signature
-keep class * extends com.google.api.client.json.GenericJson {
*;
}
-keep class com.google.api.services.** {
*;
}

-dontwarn com.google.android.gms.cast.**
-dontwarn com.google.android.gms.**
-keep class com.google.android.gms.** { *; }

-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
# End google api

# Other
-optimizations !class/unboxing/enum
-dontwarn com.google.code.**
-dontwarn oauth.signpost.**
-dontwarn javax.management.**
-dontwarn javax.xml.**
#-dontwarn org.apache.**
#-dontwarn org.slf4j.**
-keep class org.** { *; }
-keep class javax.** { *; }
-assumenosideeffects class * implements org.slf4j.Logger {
    public *** trace(...);
    public *** debug(...);
    public *** info(...);
    public *** warn(...);
    public *** error(...);
}

# Rx java
-dontwarn sun.misc.**
-keep class rx.internal.util.unsafe.** { *; }

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# End other


# Java mail
-dontwarn java.awt.**
-dontwarn java.beans.Beans
-dontwarn javax.security.**

-keep class javamail.** {*;}
-keep class javax.mail.** {*;}
-keep class javax.activation.** {*;}

-keep class com.sun.mail.dsn.** {*;}
-keep class com.sun.mail.handlers.** {*;}
-keep class com.sun.mail.smtp.** {*;}
-keep class com.sun.mail.util.** {*;}
-keep class mailcap.** {*;}
-keep class mimetypes.** {*;}
-keep class myjava.awt.datatransfer.** {*;}
-keep class org.apache.harmony.awt.** {*;}
-keep class org.apache.harmony.misc.** {*;}
# End java mail

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}


#-keep public class com.taasha.demo_new.Model.** {*;}
-keep public class com.example.taashaadslib.ModelClasses.** {*;}


##################################

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
