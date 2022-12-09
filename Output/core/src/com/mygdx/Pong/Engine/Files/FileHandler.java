package com.mygdx.Pong.Engine.Files;

import com.badlogic.gdx.Gdx;

import java.io.*;
import java.util.Arrays;

public class FileHandler {
    private BufferedWriter writer;
    private BufferedReader reader;
    private boolean readerClosed, writerClosed;
    private String filePath;
    private File file;

    public FileHandler(String filePath) {
            this.writerClosed = true;
            this.readerClosed = true;
            this.file = new File(filePath);
    }

    public FileHandler() {

    }

    public FileHandler(BufferedWriter writer) {
        this.writer = writer;
    }

    public String readFile(String fileName) throws IOException {
        if (reader == null) {
            setReader(new BufferedReader(new FileReader(fileName)));
        }

        if (!isWriterClosed()) {
            throw new IOException("BufferedWriter instance is still open to this file");
        }

        try {
            char[] fileContent = new char[5000];
            reader = new BufferedReader(new FileReader(fileName));
            reader.read(fileContent, 0, 5000);
            reader.close();
            readerClosed = true;
            return Arrays.toString(fileContent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    public void writeFile(String fileContent) {
        if (writer == null) {
            throw new RuntimeException("BufferedWriter instance in class 'FileHandler' is null");
        }

        try {
            writer.write(fileContent);
            writer.close();
            writerClosed = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void createFile() {
        try {
            Gdx.files.local(file.getName()).writeString("", false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createFile(String fileName) {
        Gdx.files.local(fileName).writeString("", false);
    }

    public boolean fileExists() {
        return file.exists();
    }

    public void closeReader() {
        try {
            reader.close();
            readerClosed = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeWriter() {
        try {
            writer.close();
            writerClosed = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isReaderClosed() {
        return this.readerClosed;
    }

    public boolean isWriterClosed() {
        return this.writerClosed;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
    }

    public void setFile(File file) {
        this.filePath = file.getPath();
        this.file = file;
    }

    public BufferedReader getReader() {
        return this.reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public BufferedWriter getWriter() {
        return this.writer;
    }
}