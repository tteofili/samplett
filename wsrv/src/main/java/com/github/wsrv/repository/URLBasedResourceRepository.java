package com.github.wsrv.repository;

import com.github.wsrv.Resource;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * @author tommaso
 */
public class URLBasedResourceRepository implements ResourceRepository {
  private String root;

  @Override
  public void initialize(String root) {
    this.root = root;
  }

  @Override
  public Resource getResource(String resourceName) throws ResourceNotFoundException, NotReadableResourceException {
    Resource resource = null;
    try {
      URL url = URI.create(new StringBuilder(root).append(resourceName).toString()).toURL();
      URLConnection urlConnection = url.openConnection();
      InputStream remoteStream = urlConnection.getInputStream();
      final byte[] remoteResourceInBytes = IOUtils.toByteArray(remoteStream);
      remoteStream.close();
      return new Resource() {
        @Override
        public byte[] getBytes() {
          return remoteResourceInBytes;
        }
      };
    } catch (SocketTimeoutException e) {
      throw new NotReadableResourceException(e.getLocalizedMessage());
    } catch (MalformedURLException e) {
      throw new ResourceNotFoundException(e);
    } catch (IOException e) {
      throw new ResourceNotFoundException(e);
    }
  }
}
