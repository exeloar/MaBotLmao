package main;
import bots.MyBot;

import java.util.HashMap;
import java.util.Random;

import com.cavariux.twitchirc.Chat.Channel;
import com.cavariux.twitchirc.Chat.User;

class MaBotChannel{
	public Channel channel;
	public long lastTimeStamp;
	public long cooldownAmount;
	public int replacementProbability;
	
	MaBotChannel(Channel c, long cooldown, int probability){
		channel = c;
		lastTimeStamp = -1;
		cooldownAmount = cooldown;
		replacementProbability = probability;
	}
	
	public String toString() { return channel.toString(); }
}

public class Main {
	public final static long version = 3L;
	
	
	public static HashMap<String,MaBotChannel> channels = new HashMap<String,MaBotChannel>();
	public static MyBot bot = new MyBot();
	public static Random random = new Random();

	
	private static void initialize() {
		bot.joinChannel("#mabotlmao");
		//Load users from file
	}
	
	public static void main(String[]args) {
		bot.connect();
		initialize();
		bot.start();
	}
	
	public static void addBotToChannel(User user) {
		MaBotChannel entry = channels.get(user.toString());
		if(entry==null){
			entry = new MaBotChannel(bot.joinChannel("#"+user.toString()),1000,1);
			channels.put(user.toString(),entry);
			bot.sendMessage("ma bot lmao running version " + version, entry.channel);
			bot.whisper(user, "Successfully added ma bot lmao");
		}
		else { bot.whisper(user, "Failed to add bot, already exists in your channel"); }
	}
	public static void removeBotFromChannel(User user) {
		MaBotChannel entry = channels.get(user.toString());
		if(entry!=null){
			bot.sendMessage("goodbyema", entry.channel);
			bot.partChannel(entry.toString());
			channels.remove(user.toString());
			bot.whisper(user, "Successfully removed ma bot lmao");
		}
		else { bot.whisper(user, "Failed to remove bot, does not exist in your channel"); }
	}
	public static void setDelay(User user, String delay) {
		MaBotChannel entry = channels.get(user.toString());
		if(entry!=null){
			try{
				entry.cooldownAmount = Long.parseLong(delay);
				bot.whisper(user, "Successfully changed cooldown to "+Long.parseLong(delay)+" ms");
			}
			catch(Exception e) {
				bot.whisper(user, "Cooldown amount not recognized");
			}
		}
		else { bot.whisper(user, "Failed to change cooldown, bot does not exist in your channel"); }
	}
	public static void setRatio(User user, String ratio) {
		MaBotChannel entry = channels.get(user.toString());
		if(entry!=null){
			try{
				entry.replacementProbability = Integer.parseInt(ratio);
				bot.whisper(user, "Successfully changed probability ratio to 1:"+Long.parseLong(ratio));
			}
			catch(Exception e) {
				bot.whisper(user, "Probability ratio not recognized");
			}
		}
		else { bot.whisper(user, "Failed to change probability ratio, bot does not exist in your channel"); }
	}

	public static boolean readyToSendMessage(Channel channelMa) {
		MaBotChannel entry = channels.get(channelMa.toString().substring(1));
		return 
				entry.lastTimeStamp == -1 || 
				Math.abs(System.currentTimeMillis() - entry.lastTimeStamp) >= entry.cooldownAmount
		;
	}

	public static boolean dueToPost(Channel channelMa) { return random.nextInt(channels.get(channelMa.toString().substring(1)).replacementProbability) == 0; }
}
