package main;
import bots.MyBot;

import com.cavariux.twitchirc.Chat.Channel;

public class Main {
	
	final static String[] channels = {"#exeloar", "#capp_kap"};
	static long[] channelLastMaClocks = new long[channels.length]; 
	static int[] channelCooldownMaAmounts = new int[channels.length];
	static int[] probabilityMaReplacements = new int[channels.length];
	
	final static long version = 3L;
	
	public static long lastMaClock = -1000000L;
	
	private static void initializeLastMaClocks() { 
		for(int i = 0; i < channels.length; i++) { channelLastMaClocks[i] = -1; }
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
	
	private static void initializeCooldownMaAmounts() {
		channelCooldownMaAmounts[0] = 0;
		for(int i = 1;i<channels.length;i++) {
			channelCooldownMaAmounts[i] = 5;
		}
	}

	public static int getCooldownMaAmount(Channel channelMa) throws Exception {
		String channelNameMa = channelMa.toString();
		for(int i = 0; i < channels.length; i++) {
			if(channelNameMa.equalsIgnoreCase(channels[i])) {
				return channelCooldownMaAmounts[i];
			}
		}
		throw new Exception("Channel does not exist");
	}
	
	private static void initializeProbabilityMaAmounts() {
		probabilityMaReplacements[0] = 1;
		for(int i = 1;i<channels.length;i++) {
			probabilityMaReplacements[i] = 1;
		}
	}

	public static int getProbabilityMaAmount(Channel channelMa) throws Exception {
		String channelNameMa = channelMa.toString();
		for(int i = 0; i < channels.length; i++) {
			if(channelNameMa.equalsIgnoreCase(channels[i])) {
				return probabilityMaReplacements[i];
			}
		}
		throw new Exception("Channel does not exist");
	}
	

	
	private static void initialize() {
		initializeLastMaClocks();
		initializeCooldownMaAmounts();
		initializeProbabilityMaAmounts();
	}
	
	public static void main(String[]args) {
		
		initialize();

		MyBot bot = new MyBot();
		bot.connect();
		for(String channel : channels) {
			bot.sendMessage("ma bot lmao running version " + version, bot.joinChannel(channel));
		}
		bot.start();
	}
}
