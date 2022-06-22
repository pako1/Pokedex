//name of gradle project
rootProject.name = "Pokedex"

//from where gradle should get its plugins!
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "dagger.hilt.android.plugin") {
                useModule("com.google.dagger:hilt-android-gradle-plugin:2.41")
            }
        }
    }
}
//At which repositories gradle will look for our dependencies
dependencyResolutionManagement {
    //Only from the settings we should look for dependencies and not directly
    //from the project(s)/modules and if it will be done like it it will fail!
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

//Every gradle consists out of subprojects/modules. In our case, app is the subproject
//in case we had other modules we would have to include them
include(":app")
