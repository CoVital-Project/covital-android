# CoVital Android App

[![CircleCI](https://circleci.com/gh/CoVital-Project/covital-android.svg?style=svg)](https://circleci.com/gh/CoVital-Project/covital-android)

## Getting Setup with Android Studio

* [Download the latest Android Studio](https://developer.android.com/studio)
* Download resources in Android SDK Manger: Android Build Tools to 29.0.3 and Android SDK 29
* Add the latest Kotlin plugin V1.3.61 within Android Studio

## Building the Project

Opening the project should trigger a gradle sync.

```
./gradlew :app:clean; ./gradlew :app:assembleInternalMedDebug;
```

## Running Tests

CircleCI runs all of our unit tests. An example command for the `app` module is:

```
./gradlew :app:testInternalMedDebugUnitTest
```

To run all unit tests at once

```
./gradlew testInternalMedDebugUnitTest
```

To run our integration test suite locally:

```
./gradlew :app:connectedInternalMedDebugAndroidTest
```

## Contributing

Please see our [guide on contributing](https://github.com/CoVital-Project/covital-android/blob/develop/CONTRIBUTING.md), it explains how we work, resources to get started, and what we need help with.

## Code Style Guide

* [Kotlin Code Conventions](https://kotlinlang.org/docs/reference/coding-conventions.html)
* [Android Kotlin Guides](https://android.github.io/kotlin-guides/style.html)

## Issue Tracking

You can find everything in [GitHub Issues](https://github.com/CoVital-Project/covital-android/issues).

To see what we're currently working on look at the [Android Launch Project](https://github.com/CoVital-Project/covital-android/projects/1).

## Alert Monitoring

We collect and send anonymous data to these repositories:
* Crash reports to [Firebase Crashlytics](https://console.firebase.google.com/u/2/project/helpful-monitoring-o2-dev/crashlytics/app/android:org.covital.internal/issues)
* Performance data on network requests to [Firebase Performance](https://console.firebase.google.com/u/2/project/helpful-monitoring-o2-dev/performance/app/android:org.covital.internal/trends)
* Metrics to [Firebase Analytics](https://console.firebase.google.com/u/2/project/helpful-monitoring-o2-dev/analytics/app/android:org.covital.internal/overview)

## Publishing

We publish our internal builds on [Firebase App Distribution](https://console.firebase.google.com/u/2/project/helpful-monitoring-o2-dev/appdistribution/app/android:org.covital.internal/releases).
