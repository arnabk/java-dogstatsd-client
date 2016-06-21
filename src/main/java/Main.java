import com.github.arnabk.statsd.BlockingStatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;

public class Main {
	public static void main(String args[]) {
		BlockingStatsDClient client = new BlockingStatsDClient("test", "localhost", 8125);
		client.count("debraj", 1, 0.9, (String[])null);
		client.count("manna", 1, (String[])null);
		client.stop();
	}
}
