package util.impl;

import model.enums.LogTypes;
import model.exceptions.FileOperationException;
import model.exceptions.NotFoundException;
import util.FileUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileUtility implements FileUtil {
    private static final String GENERAL_PATH_BY_DATE = "logs/%2";
    private static final String FULL_PATH = "%s/%s.txt";
    public static AtomicBoolean isCreatedFileAndDirectory = new AtomicBoolean(false);
    @Override
    public void readFile(String fileName) {
        try (InputStream inputStream = new FileInputStream(getFile(fileName,false))) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            byte[] bytes = bufferedInputStream.readAllBytes();
            System.out.println(new String(bytes));
        } catch (IOException exception) {
            throw new FileOperationException(exception.getMessage());
        }
    }

    @Override
    public void writeFile(String fileName, String data) {
        try {
            File file = getFile(fileName, true);

            OutputStream outputStream = new FileOutputStream(file,true);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(data.getBytes());

            bufferedOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private File getFile(String fileName, boolean isWrite) throws IOException {
        File file = new File(String.format(FULL_PATH, getDirectory(), fileName));

        if (!file.exists() && isWrite) {
            boolean isCreatedFile = file.createNewFile();
            if(!isCreatedFile) {
                throw new FileOperationException("File does not created: " + fileName);
            }
            initialDataOfFile(LogTypes.getLogTypesByFileName(fileName));
            isCreatedFileAndDirectory.set(true);
        } else if (!file.exists() && !isWrite) {
            throw new NotFoundException("File not found: " + fileName);
        }
        return file;
    }
    private String getDirectory() {
        LocalDate localDate = LocalDate.now();
        String fullDirectory = String.format(GENERAL_PATH_BY_DATE, localDate);
        File generalPath = new File(fullDirectory);

        if (!generalPath.exists()) {
            boolean isCreatedGeneralPath = generalPath.mkdirs();

            if (!isCreatedGeneralPath) {
                throw new FileOperationException("Directory does not created: " + fullDirectory);
            }
        }
        return fullDirectory;
    }

    private void initialDataOfFile(LogTypes logTypes) {
        if (logTypes != null) {
            writeFile(logTypes.getFileName(), logTypes.name() + " LOGS - " + LocalDate.now() + "\n");
        }
    }
}
