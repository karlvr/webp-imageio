/*--------------------------------------------------------------------------
 *  Copyright 2007 Taro L. Saito
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *--------------------------------------------------------------------------*/
//--------------------------------------
// SQLite JDBC Project
//
// SQLite.java
// Since: 2007/05/10
//
// $URL$
// $Author$
//--------------------------------------
package com.luciad.imageio.webp;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.luciad.imageio.webp.util.OSInfo;

/**
 * The library files are automatically extracted from this project's package
 * (JAR).
 * <p/>
 * usage: call {@link #initialize()} before using the library.
 *
 * @author leo
 */
public class NativeLoader {

    /**
     * Loads native library.
     *
     * @return True if native library is successfully loaded; false
     * otherwise.
     */
    public static synchronized boolean initialize() throws Exception {
        return loadNativeLibrary();
    }

    /**
     * Loads native library using given path and name of the library.
     *
     * @throws
     */
    private static boolean loadNativeLibrary() throws Exception {
        try {
            System.loadLibrary("webp-imageio");
            return true;
        } catch (Throwable t) {
            System.err.println("Failed to load native webp-imageio library: " + t.getMessage());
            return false;
        }
    }


    @SuppressWarnings("unused")
    private static void getNativeLibraryFolderForTheCurrentOS() {
        String osName = OSInfo.getOSName();
        String archName = OSInfo.getArchName();
    }

    /**
     * @return The major version of the library.
     */
    public static int getMajorVersion() {
        String[] c = getVersion().split("\\.");
        return (c.length > 0) ? Integer.parseInt(c[0]) : 1;
    }

    /**
     * @return The minor version of the library.
     */
    public static int getMinorVersion() {
        String[] c = getVersion().split("\\.");
        return (c.length > 1) ? Integer.parseInt(c[1]) : 0;
    }

    /**
     * @return The version of the library.
     */
    public static String getVersion() {

        URL versionFile = NativeLoader.class.getResource("/META-INF/maven/org.sejda.imageio/webp-imageio/pom.properties");
        if (versionFile == null) {
            versionFile = NativeLoader.class.getResource("/META-INF/maven/org.sejda.imageio/webp-imageio/VERSION");
        }

        String version = "unknown";
        try {
            if (versionFile != null) {
                Properties versionData = new Properties();
                versionData.load(versionFile.openStream());
                version = versionData.getProperty("version", version);
                version = version.trim().replaceAll("[^0-9\\.]", "");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return version;
    }

}
