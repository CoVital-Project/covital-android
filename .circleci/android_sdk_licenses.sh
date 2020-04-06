#!/usr/bin/env bash

mkdir -p /opt/android/sdk/licenses || true
cp ./licenses/* /opt/android/sdk/licenses/
yes | $ANDROID_HOME/tools/bin/sdkmanager "build-tools;29.0.3"
