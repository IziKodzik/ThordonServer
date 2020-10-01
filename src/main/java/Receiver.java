import java.io.InputStream;
import java.net.Socket;

public interface Receiver {

	byte[] receive(InputStream input);

}

