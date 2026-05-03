#!/bin/bash
# Setup script for ManageMyWallet Android build
# Run this once to install dependencies

set -e

echo "=== ManageMyWallet Build Setup ==="

# Check if Homebrew is installed
if ! command -v brew &> /dev/null; then
    echo "Homebrew not found. Install it first:"
    echo "  /bin/bash -c \"\$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)\""
    exit 1
fi

# Install Java 17 (required for AGP 8.x)
if ! command -v java &> /dev/null || ! java -version 2>&1 | grep -q "17"; then
    echo "Installing Java 17..."
    brew install openjdk@17
    sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
    echo "export PATH=\"/opt/homebrew/opt/openjdk@17/bin:\$PATH\"" >> ~/.zshrc
    export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"
fi

# Install Android SDK via command line tools
if [ ! -d "$HOME/Android/sdk" ]; then
    echo "Installing Android SDK..."
    brew install android-commandlinetools
    mkdir -p $HOME/Android/sdk/cmdline-tools
    export ANDROID_HOME=$HOME/Android/sdk
    export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH

    # Accept licenses and install required packages
    sdkmanager --install "platforms;android-34" "build-tools;34.0.0" "platform-tools" --sdk_root=$ANDROID_HOME
fi

echo ""
echo "=== Setup Complete ==="
echo "Now run: ./gradlew assembleDebug"
