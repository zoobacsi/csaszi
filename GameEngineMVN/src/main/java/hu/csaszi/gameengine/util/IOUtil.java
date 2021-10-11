/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package hu.csaszi.gameengine.util;


import hu.csaszi.gameengine.example.SimpleGame;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.BufferUtils.createByteBuffer;

public final class IOUtil {

    private IOUtil() {
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read
     * @param bufferSize the initial buffer size
     * @return the resource data
     * @throws IOException if an IO error occurs
     */
    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                    InputStream source = IOUtil.class.getClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
                    }
                }
            }
        }

        buffer.flip();
        return buffer.slice();
    }

    public static File getFile(String fileName) {

        return getFile(fileName, "");
    }
    public static File getFile(String fileName, String directory) {

        File fsFile = new File(directory + fileName);

        if(fsFile.exists()) {
            return fsFile;
        }

        String current = null;
        try {
            current = new File( "." ).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = SimpleGame.class.getClassLoader().getResourceAsStream(fileName);

        if (is == null) {
            System.out.println("1");
            is = SimpleGame.class.getClassLoader().getResourceAsStream(directory + fileName);
        }
        if (is == null) {
            System.out.println("2");
            is = SimpleGame.class.getClassLoader().getResourceAsStream("src/main/resources/" + directory + fileName);
        }

        File file = null;
        try {

            byte[] buffer = new byte[is.available()];
            is.read(buffer);

            System.out.println("dir: " + directory + " file: " + fileName);
            File targetFile = File.createTempFile("temp","temp");
            System.out.println("tempPath: " + targetFile.getPath());
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            outStream.write(10);
            outStream.flush();
            outStream.close();

            is.close();

//            file = new File(url.getFile());
//            System.out.println(file);
//            if (file == null) {
//                throw new Error("Missing file " + directory + fileName);
//            }
            file = targetFile;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
}