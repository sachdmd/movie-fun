package org.superbiz.moviefun.blobstore;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class FileStore implements BlobStore {

    @Override
    public void put(Blob blob) throws IOException {
        targetFile.delete();
        targetFile.getParentFile().mkdirs();
        targetFile.createNewFile();

        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            outputStream.write(uploadedFile.getBytes());
        }
        // ...
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        // ...
        return null;
    }

    @Override
    public void deleteAll() {
        // ...
    }
}
