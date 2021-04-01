package dependencies

object Compose {

    const val version = "1.0.0-beta03"
    private const val constraint_layout_version = "1.0.0-alpha03"
    private const val compose_activity_version = "1.3.0-alpha04"
    private const val compose_viewModel_version = "1.0.0-alpha02"

    const val compose_ui = "androidx.compose.ui:ui:${version}"
    // Tooling support (Previews, etc.)androidx.compose.ui:ui-tooling1.0.0-alpha09
    const val compose_tooling = "androidx.compose.ui:ui-tooling:${version}"
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    const val compose_foundation = "androidx.compose.foundation:foundation:${version}"

    // Material Design
    const val compose_material = "androidx.compose.material:material:${version}"


    // Material design icons
    const val compose_material_icons = "androidx.compose.material:material-icons-core:${version}"
    const val compose_material_icons_ext =
        "androidx.compose.material:material-icons-extended:${version}"

    const val compose_activity = "androidx.activity:activity-compose:$compose_activity_version"

    const val compose_viewModel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:$compose_viewModel_version"

    // Integration with observables
    const val compose_livedata = "androidx.compose.runtime:runtime-livedata:${version}"

    const val constraint_layout =
        "androidx.constraintlayout:constraintlayout-compose:$constraint_layout_version"

// UI Tests
  //  androidTestImplementation 'androidx.compose.ui:ui-test-junit4:1.0.0-beta01'
//    const val compose_compiler = "androidx.compose.compiler:compiler:1.0.0-alpha10"

//    const val compose_animation = "androidx.compose.animation:animation:${version}"

//    const val compose_run_time = "androidx.compose.runtime:runtime:${version}"
}