package bots;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.cavariux.twitchirc.Chat.Channel;
import com.cavariux.twitchirc.Chat.User;
import com.cavariux.twitchirc.Core.TwitchBot;

import main.Main;

public class MyBot extends TwitchBot{
	
	final String[] helpMessage = {
			"Put a ! at the start of your message to start a command. The available commands are:",
			"helpma - Display this message",
			"addma - Add the bot to your channel",
			"removema - Remove the bot from your channel",
			"setdelayma ### -  Set the delay (in milliseconds) between ma messages lmao [default 1000]",
			"setratioma ### - Set the chance of replacement (1:###) ex. 1 = 100%, 2 = 50%, etc. [default 1]",
			"--More Configuration Settings Are Planned--"
	};
	final String[] triggers = {"ma", "dese", "dragon", "dixon", "sucon","sawcon", "saw", "joe", "candice", "bofa", "vitamin c", "tess", "jenna", "goblin"};
	final char[] ignores = {'.',',','?','!',':',';','(',')','[','*','$','#','%','^',']','{','}','\'','\"','/','\\','-','`','~','_'};
	
	final String[] devs = {"exeloar","youreverydaygiantclam"};
	
	class MessageInfo{ public int index; public String trigger; }
	
	public MyBot() {
		try {
			Scanner input = new Scanner(new File("user.txt"));
			String[] info = input.nextLine().split(",");         //Format : username,oauthkey
			this.setUsername(info[0]);
			this.setOauth_Key(info[1]);
		} 
		catch (FileNotFoundException e) { System.out.println("User File not found"); System.exit(1); }
	}
	
	
	
	public void onWhisper(User user, String message) {
		message = message.replaceAll("!", "");
		
		//Developer Commands
		if(checkIfDev(user)) {
			if(checkDevCommand(message)) { return; }
		}
		
		//User Commands
		switch(message.split(" ")[0]) {
			case("addma"): 				{ Main.addBotToChannel(user); 										break;}
			case("removema"): 			{ Main.removeBotFromChannel(user);	  								break;}
			case("setdelayma"):{    try	{ Main.setDelay(user,message.split(" ")[1]);}catch(Exception e){}  	break;} //FIXME: double split :(
			case("setratioma"):{    try	{ Main.setRatio(user,message.split(" ")[1]);}catch(Exception e){}  	break;} //FIXME: double split :(
		}
	}
	
	public void onMessage(User user, Channel channel, String message) {
		
		if(channel.toString().equals("#mabotlmao")) { onWhisper(user,message); return; } //commands
		
		MessageInfo info = new MessageInfo();
		if(!Main.readyToSendMessage(channel))	{ return; } //cooldown check
		if(!hasHumor(message,info)) 			{ return; } //message has no humor.
		if(!Main.dueToPost(channel)) 			{ return; } //probability check
			
		String newMessage = message.substring(0,info.index);
			
		//No unnecessary spaces before replacement
		String before = message.substring(0,info.index);
		String[] split = before.split(" ");
		before = split[split.length-1];
		if(!isIgnores(before)) { newMessage+= " "; }
		
		newMessage += message.substring(info.index,info.index + info.trigger.length());
		
		switch(info.trigger) {
			case("candice"): 	
				{ newMessage = newMessage.substring(0,newMessage.length()-4) + " dice dick fit in ya mouth"; 	break; 	}
			case("dragon"):
				{ newMessage += " dice dick on ya face"; 														break; 	}
			case("dixon"):		
				{ newMessage = newMessage.substring(0,newMessage.length()-2) + " on ya face"; 					break;	}
			case("joe"):
				{ newMessage += " mama";																		break;	}
			case("tess"):		
				{ newMessage += " tickles";																		break;	}
			case("jenna"):		
				{ newMessage += " talls";																		break;	}
			
			
			case("saw"):		
				{ newMessage += " con "; 																				}
			case("bofa"):case("sucon"):case("sawcon"):case("vitamin c"):case("goblin"):		
				{ newMessage += " dese";																				}
			default:
				{ newMessage += " balls";																				}
		}
		newMessage += " lmao";
		
		
		this.sendMessage(newMessage,channel);
		Main.updateCooldown(channel);
		return;
	}

	private boolean checkIfDev(User user) {
		for(String dev : devs) {
			if(user.toString().equals(dev)) { return true; }
		}
		return false;
	}
	
	private boolean checkDevCommand(String message) {
		switch(message.split(" ")[0]) {
			case("restartma"): 			{ Main.restartMaBot(); 											  	break;}
			case("numberma"): 			{ Main.numberMaUsers();  										  	break;}
			case("checkuserma"):{    try{ Main.checkMaUsers(message.split(" ")[1]);}catch(Exception e){}  	break;} //FIXME: double split :(
			default:					{ return false; }
		}
		return true;
	}
	
	private final boolean hasHumor(String message, MessageInfo info) {
		for(int i = 0;i<triggers.length;i++) {
			info.trigger = triggers[i];
			info.index = message.toLowerCase().lastIndexOf(info.trigger);
			if(messageHumorCheck(info.index,message,info.trigger)) { return true; }
		}
		return false;
	}
	
	private final boolean messageHumorCheck(int index, String message, String trigger) {
		if(index == -1) { return false; } //no replacements found
		
		String before = message.substring(0,index);
		String[] split = before.split(" ");
		before = split[split.length-1];
		
		String after = message.substring(Math.min(message.length(),index + trigger.length())).split(" ")[0];
		
		System.out.println("Before:{"+before+"}, After:{"+after+"}, splitlength:"+split.length);
		
		if(split.length == 1) { return index + trigger.length() + after.length() == message.length(); } //do not replace first word of long message
		
		return isIgnores(before) || isIgnores(after); //at the start or end of word? (disregarding ignored characters like punctuation)
	}
	
	private final boolean isIgnores(String s) {
		for(int i = 0; i< s.length();i++) {
			boolean found = false;
			for(int j = 0;j<ignores.length;j++) {
				if(s.charAt(i) == ignores[j]) {
					found = true; 
					j = ignores.length;
				}
			}
			if(!found) { return false; }
		}
		return true;
	}
}




//allma and no cutoff
/*this.sendMessage(
		"allma and no cutoff: " 
		+ message.replaceAll("ma", " ma balls lmao ").replaceAll("Ma", " Ma balls lmao ").replaceAll("MA", " MA balls lmao "),
		channel);
*/

//lastma and no cutoff
/*this.sendMessage(
		"lastma and no cutoff: "
		+ message.substring(0,indexMa) + " " + message.substring(indexMa,indexMa + 2) + " balls lmao " + message.substring(indexMa+2), 
		channel);
*/
