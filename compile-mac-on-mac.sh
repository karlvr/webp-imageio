#!/bin/zsh -e
#
# Compile macOS native library for x86_64 and arm64 on macOS
# Don't link to png, jpeg, tiff or gif libraries as cmake thinks it can link on both architectures but
# I only have x86_64 libraries installed.

mkdir -p build/Mac/x86_64
pushd build/Mac/x86_64
cmake -DCMAKE_OSX_ARCHITECTURES="x86_64" -DCMAKE_OSX_DEPLOYMENT_TARGET=10.9 -DPNG_LIBRARY_RELEASE= -DJPEG_LIBRARY_RELEASE= -DTIFF_LIBRARY_RELEASE= -DGIF_LIBRARY= ../../..
cmake --build . --config Release
popd

mkdir -p out/Mac/x86_64
cp build/Mac/x86_64/src/main/c/libwebp-imageio.dylib out/Mac/x86_64/

mkdir -p build/Mac/aarch64
pushd build/Mac/aarch64
cmake -DCMAKE_OSX_ARCHITECTURES="arm64" -DCMAKE_OSX_DEPLOYMENT_TARGET=10.9 -DPNG_LIBRARY_RELEASE= -DJPEG_LIBRARY_RELEASE= -DTIFF_LIBRARY_RELEASE= -DGIF_LIBRARY= ../../..
cmake --build . --config Release
popd

mkdir -p out/Mac/aarch64
cp build/Mac/aarch64/src/main/c/libwebp-imageio.dylib out/Mac/aarch64/

# Create a universal library
lipo -output out/Mac/libwebp-imageio.dylib -create build/Mac/aarch64/src/main/c/libwebp-imageio.dylib build/Mac/x86_64/src/main/c/libwebp-imageio.dylib
