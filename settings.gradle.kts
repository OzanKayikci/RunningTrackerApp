pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // e.g this is how you would add jitpack
        maven("https://jitpack.io")
    }
}

rootProject.name = "RunningTrackerApp"
include(":app")
 