package com.github.marschall.jsr203efs;
import java.nio.file.Path;

import org.eclipse.core.filesystem.IFileStore;


abstract class EfsPath implements Path {
  
  private final IFileStore fileStore;

  EfsPath(IFileStore fileStore) {
    this.fileStore = fileStore;
  }
  
  IFileStore getFileStore() {
    return this.fileStore;
  }
  
  @Override
  public EfsPath getParent() {
    // TODO relative path
    IFileStore parent = this.fileStore.getParent();
    if (parent != null) {
      return newPath(parent);
    } else {
      return null;
    }
  }
  
  @Override
  public Path resolve(String other) {
    IFileStore child = this.fileStore.getChild(other);
    return newPath(child);
  }
  
  private EfsPath newPath(IFileStore parent) {
    return new EfsPath(parent);
  }

}
