// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.application).apply(false)
    alias(libs.plugins.kotlin).apply(false)
    alias(libs.plugins.kotlin.kapt).apply(false)
    alias(libs.plugins.dagger.hilt.plugin).apply(false)
    alias(libs.plugins.apollo.graphql.plugin).apply(false)
}