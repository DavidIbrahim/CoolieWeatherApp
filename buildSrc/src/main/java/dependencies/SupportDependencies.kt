package dependencies

import dependencies.Versions

object SupportDependencies {

    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val material_design = "com.google.android.material:material:${Versions.material_design}"
    const val swipe_refresh_layout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_layout}"
    const val legacy_support = "androidx.legacy:legacy-support-v4:${Versions.legacy_support}"
}