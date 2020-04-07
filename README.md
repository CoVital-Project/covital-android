# CoVital Android App

[![CircleCI](https://circleci.com/gh/CoVital-Project/covital-android.svg?style=svg)](https://circleci.com/gh/CoVital-Project/covital-android)

## Getting Setup with Android Studio

* [Download the latest Android Studio](https://developer.android.com/studio)
* Download resources in Android SDK Manger: Android Build Tools to 29.0.3 and Android SDK 29
* Add the latest Kotlin plugin V1.3.61 within Android Studio

## Building the Project

Opening the project should trigger a gradle sync.

```
./gradlew :app:clean; ./gradlew :app:assembleMedDebug;
```

## Running Tests

CircleCI runs all of our unit tests. An example command for the `app` module is:

```
./gradlew :app:testMedDebugUnitTest
```

To run all unit tests at once

```
./gradlew testMedDebugUnitTest
```

To run our integration test suite locally:

```
./gradlew :app:connectedMedDebugAndroidTest
```

## Contributing

See our [guide on contributing](https://github.com/CoVital-Project/covital-android/blob/develop/CONTRIBUTING.md) to the app and team.

When possible we should leverage the Kotlin's features to make our code more readable while also concise.

## Code Style Guide

* [Kotlin Code Conventions](https://kotlinlang.org/docs/reference/coding-conventions.html)
* [Android Kotlin Guides](https://android.github.io/kotlin-guides/style.html)

## Issue Tracking

TBD

## Alert Monitoring

TBD

## Publishing

TBD
