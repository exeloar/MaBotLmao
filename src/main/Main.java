package main;
import bots.MyBot;
import com.cavariux.twitchirc.Chat.Channel;

public class Main {
	
	final static String[] channels = {"#exeloar"};
	static long[] channelLastMaClocks = new long[channels.length]; 
	
	final static long version = 2L;
	
	public static long lastMaClock = -1000000L;
	
	private static void initializeLastMaClocks() {
		long valueMa = System.currentTimeMillis() - 45000; 
		for(int i = 0; i < channels.length; i++) { channelLastMaClocks[i] = valueMa; }
	}
	
	public static void setLastMaClock(Channel channelMa) throws Exception {
		String channelNameMa = channelMa.toString();
		for(int i = 0; i < channels.length; i++) {
			if(channelNameMa.equalsIgnoreCase(channels[i])) {
				channelLastMaClocks[i] = System.currentTimeMillis();
			}
		}
		throw new Exception("Channel does not exist");
	}
	public static long getLastMaClock(Channel channelMa) throws Exception {
		String channelNameMa = channelMa.toString();
		for(int i = 0; i < channels.length; i++) {
			if(channelNameMa.equalsIgnoreCase(channels[i])) {
				return channelLastMaClocks[i];
			}
		}
		throw new Exception("Channel does not exist");
	}
	
	public static void main(String[]args) {
		
		initializeLastMaClocks();
		MyBot bot = new MyBot();
		bot.connect();
		for(String channel : channels) {
			bot.sendMessage("ma bot lmao running version " + version, bot.joinChannel(channel));
		}
		bot.start();
	}
}
