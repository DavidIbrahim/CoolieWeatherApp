package dependencies

object Dependencies {
    const val kotlin_standard_library = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val kotlin_extensions =
        "org.jetbrains.kotlin:kotlin-android-extensions-runtime:${Versions.kotlin}"
    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val kotlin_coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines_version}"
    const val kotlin_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"
    const val kotlin_coroutines_play_services =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines_play_services}"
    const val dagger_hilt = "com.google.dagger:hilt-android:${Versions.dagger_hilt}"
    const val androidx_hilt = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.androidx_hilt}"
    const val navigation_fragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_components}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"

    const val navigation_runtime =
        "androidx.navigation:navigation-runtime:${Versions.nav_components}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_components}"
    const val navigation_dynamic =
        "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_components}"
    const val material_dialogs = "com.afollestad.material-dialogs:core:${Versions.material_dialogs}"
    const val material_dialogs_input =
        "com.afollestad.material-dialogs:input:${Versions.material_dialogs}"

    const val google_android_material =
        "com.google.android.material:material:${Versions.material_design}"
    const val androidxLifeCycle =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidx_lifeCycle}"
    const val play_core = "com.google.android.play:core:${Versions.play_core}"
    const val leak_canary = "com.squareup.leakcanary:leakcanary-android:${Versions.leak_canary}"
    const val firebase_firestore =
        "com.google.firebase:firebase-firestore-ktx:${Versions.firebase_firestore}"
    const val firebase_auth = "com.google.firebase:firebase-auth:${Versions.firebase_auth}"
    const val firebase_analytics =
        "com.google.firebase:firebase-analytics:${Versions.firebase_analytics}"
    const val firebase_crashlytics =
        "com.crashlytics.sdk.android:crashlytics:${Versions.firebase_crashlytics}"
    const val lifecycle_runtime =
        "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle_version}"
    const val lifecycle_coroutines =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2_version}"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2_version}"
    const val markdown_processor = "com.yydcdut:markdown-processor:${Versions.markdown_processor}"

    const val apollo = "com.apollographql.apollo:apollo-runtime:${Versions.apollo}"
    const val apollo_coroutines_support =
        "com.apollographql.apollo:apollo-coroutines-support:${Versions.apollo}"
    const val apollo_cache =
        "com.apollographql.apollo:apollo-normalized-cache-sqlite:${Versions.apollo}"

    const val android_preferences =
        "androidx.preference:preference-ktx:${Versions.androidx_preferences}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val view_pager2 = "androidx.viewpager2:viewpager2:${Versions.view_pager2}"

    const val converter_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.converter_moshi}"
    const val work_manager = "androidx.work:work-runtime-ktx:${Versions.work_manager}"
    const val lottie_compose = "com.airbnb.android:lottie-compose:${Versions.lottie_compose}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val googlePlayService =
        "com.google.android.gms:play-services-location:${Versions.googlePlayServices}"
    const val lifecycle_viewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    const val openWeatherMapLibrary =
        "com.github.KwabenBerko:OpenWeatherMap-Android-Library:${Versions.openWeatherMapVersion}"
}





