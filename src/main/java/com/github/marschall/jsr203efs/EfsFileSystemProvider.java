package com.github.marschall.jsr203efs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

import org.eclipse.core.filesystem.EFS;

public abstract class EfsFileSystemProvider extends FileSystemProvider {

  
  @Override
  public InputStream newInputStream(Path path, OpenOption... options) throws IOException {
    EfsPath efsPath = cast(path);
    return getFileSystem(efsPath).openInputStream(efsPath, translateOptions(options));
  }
  
  @Override
  public OutputStream newOutputStream(Path path, OpenOption... options) throws IOException {
    EfsPath efsPath = cast(path);
    return getFileSystem(efsPath).openOutputStream(efsPath, translateOptions(options));
  }
  
  private int translateOptions(OpenOption... options) {
    // TODO 
    return EFS.NONE;
  }
  
  private EfsFileSystem getFileSystem(EfsPath path) {
    // TODO 
    return null;
  }
  
  private EfsPath cast(Path path) {
    // TODO exception
    return (EfsPath) path;
  }
  
}
