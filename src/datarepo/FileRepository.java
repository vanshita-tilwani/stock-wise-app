package datarepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Implements the data repository keeping File System as data source.
 * @param <T> the type of object that needs to be saved in file system.
 */
public class FileRepository<T> implements DataRepository<T> {

  // the path of the File that servers as data source.
  private final String path;

  /**
   * Creates an instance of File repo with path as the path to the data source.
   * @param path The path to the data source.
   * @throws IOException if the path is invalid or any other errors while clearing the file.
   */
  public FileRepository(String path) throws IOException {
    this.path = path;
    // clears the data source every time a instance is created.
    Files.newInputStream(Path.of(path), StandardOpenOption.TRUNCATE_EXISTING);
  }

  @Override
  public void save(T trade) throws Exception {
    // appends the data to the file(i.e. data source).
    Files.writeString(Path.of(path), trade.toString(), StandardOpenOption.APPEND);
  }

  @Override
  public String read() throws Exception {
    // reads the data as string and returns the result.
    String result = Files.readString(Path.of(path));
    return result;
  }
}
