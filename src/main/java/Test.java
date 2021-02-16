import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) throws Exception{
		ServerSocket so = new ServerSocket(997);
		Socket c = so.accept();
		OutputStream s = c.getOutputStream();
		byte[] b = ByteBuffer.allocate(4).putInt(2137).array();
		System.out.println(Arrays.toString(b));
		s.write(b);

	}

}
