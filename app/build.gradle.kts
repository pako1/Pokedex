//Plugins add extra functionality to the application such as gradle tasks!
//without having us writing build scripts to create new tasks
plugins {
    alias(libs.plugins.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.plugin)
    alias(libs.plugins.apollo.graphql.plugin)
}
//Instead of building manually the project aka javac all files we use
//gradle which is a build tool that will do the build for us.
//It requires info/configuration on how to build the app. This happens via
//the build.gradle file/build script (this). Gradle will then add tasks which will
//allow us to build/test etc the app.
//Lastly,there is gradle wrapper(gradlew) which contains all the tasks which we can execute!
android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.example.pokedex"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.example.pokedex.CustomTestRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        animationsDisabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

apollo {
    packageName.set("com.example.pokedex")
    generateKotlinModels.set(true)
    generateOptionalOperationVariables.set(false)
    generateTestBuilders.set(true)
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.coreKtx)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    implementation(libs.coil)
    implementation(libs.material.design)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.fragment)
    implementation(libs.bundles.espresso)
    androidTestImplementation(libs.bundles.test)
    implementation(libs.lottie)
    implementation(libs.jake.timber)
    androidTestImplementation(libs.coroutine.test)
    androidTestImplementation(libs.autoUI)
    androidTestImplementation(libs.androidx.core)
    implementation(libs.square.okhttp)
    implementation(libs.unit.junit)
    androidTestImplementation(libs.android.test)
    implementation(libs.apollo.graphql)
    androidTestImplementation(libs.instrumented.mockk)
    testImplementation(libs.unit.mockk)
    implementation(libs.android.hilt)
    kapt(libs.android.hilt.compiler)
    androidTestImplementation(libs.android.hilt.testing)
    kaptAndroidTest(libs.android.hilt.compiler)
    implementation(libs.bundles.apollo)
    implementation(libs.bundles.room)
    kapt(libs.android.room.compiler)
}