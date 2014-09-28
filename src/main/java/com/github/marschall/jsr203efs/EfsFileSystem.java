package com.github.marschall.jsr203efs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemException;

import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

// http://eclipsezone.com/forums/thread.jspa?messageID=92063570
// http://www.eclipsezone.com/articles/efs/
// https://wiki.eclipse.org/EFS
// http://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2FresAdv_efs_api.htm
abstract class EfsFileSystem extends FileSystem {

  private final IFileSystem fileSystem;

  EfsFileSystem(IFileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  @Override
  public boolean isReadOnly() {
    // TODO open check
    return !this.fileSystem.canDelete() && !this.fileSystem.canWrite();
  }

  InputStream openInputStream(EfsPath path, int options) throws IOException {
    try {
      return path.getFileStore().openInputStream(options, null);
    } catch (CoreException e) {
      throw translateException(path, e);
    }
  }
  
  private FileSystemException translateException(EfsPath path, CoreException exception) {
    IStatus status = exception.getStatus();
    if (status instanceof IResourceStatus) {
      IResourceStatus resourceStatus = (IResourceStatus) status;
      FileSystemException fileSystemException = new FileSystemException(resourceStatus.getPath().toString(), null, exception.getMessage());
      fileSystemException.initCause(exception);
      return fileSystemException;
    } else {
      FileSystemException fileSystemException = new FileSystemException(path.toString(), null, exception.getMessage());
      fileSystemException.initCause(exception);
      return fileSystemException;
    }
    
  }
  
  OutputStream openOutputStream(EfsPath path, int options) throws IOException {
    try {
      return path.getFileStore().openOutputStream(options, null);
    } catch (CoreException e) {
      throw translateException(path, e);
    }
  }

}
