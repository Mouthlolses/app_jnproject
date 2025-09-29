import java.util.Properties

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
    }
}

rootProject.name = "App_jnproject"
include(":app")
include(":core:network")
include(":core:data")
