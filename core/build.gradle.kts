import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.ProjectModules.api

plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    api(Dependencies.Dagger.dagger)
    api(Dependencies.Dagger.daggerAndroid)
    api(Dependencies.Dagger.daggerAndroidSupport)

    implementation(Dependencies.AndroidX.Compose.runtime)
    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)
    annotationProcessor(Dependencies.AndroidX.lifecycleCompiler)
    annotationProcessor(Dependencies.AndroidX.archViewModel)
    implementation(Dependencies.AndroidX.archComponents)
}