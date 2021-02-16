import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Base64;

public class Test {

	public static void main(String[] args) throws Exception{
		ServerSocket so = new ServerSocket(997);
		System.out.println("ios endpoint");
		Socket cl = so.accept();
		byte[] response =(Base64.getEncoder().encode("jebac disa".getBytes()));


		cl.getOutputStream().write(response);


	}

}
