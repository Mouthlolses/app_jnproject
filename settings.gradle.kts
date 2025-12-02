import java.util.Properties

include(":admin")


pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                val props = Properties()
                File(rootDir, "local.properties").inputStream().use { props.load(it) }
                password = props.getProperty("MAPBOX_DOWNLOADS_TOKEN")
            }
        }
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "App_jnproject"
include(":app")
include(":core:network")
include(":core:data")
