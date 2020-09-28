import java.nio.ByteBuffer;

public class DefaultLayer
	implements Layer{

	@Override
	public boolean process(byte[] request, byte[] response) {
		String message = "Wisited default layer";
		request = ByteBuffer.allocate(request.length + message.length() ).put(request).put(message.getBytes()).array();
		return true;
	}
}
