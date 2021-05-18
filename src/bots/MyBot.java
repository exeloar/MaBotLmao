package bots;

import java.util.Random;

import com.cavariux.twitchirc.Chat.Channel;
import com.cavariux.twitchirc.Chat.User;
import com.cavariux.twitchirc.Core.TwitchBot;

import main.Main;

public class MyBot extends TwitchBot{
	
	public MyBot() {
		this.setUsername("mabotlmao");
		this.setOauth_Key("oauth:km7xcs8h16m032o7l6ky90z5v9mf48");
	}
	
	final String[] helpMessage = {
			"Put a ! at the start of your message to start a command. The available commands are:",
			"helpma - Display this message",
			"addma - Add the bot to your channel",
			"removema - Remove the bot from your channel",
			"setdelayma ### -  Set the delay (in milliseconds) between ma messages lmao [default 1000]",
			"setratioma ### - Set the chance of replacement (1:###) ex. 1 = 100%, 2 = 50%, etc. [default 1]",
			"--More Configuration Settings Are Planned--"
	};
	
	final String[] triggers = {"ma", "dese", "dragon", "sucon","sawcon", "saw", "joe", "candice", "bofa"};
	
	
	public void onWhisper(User user, String messageMa) {
		messageMa = messageMa.replaceAll("!", "");
		
		//DEBUG COMMANDS
		if(user.toString().equals("exeloar")) {
			if(messageMa.equals("restartma")) { Main.restartMaBot(); }
			if(messageMa.equals("numberma")) { Main.numberMaUsers(); }
			try{if(messageMa.split(" ")[0].equals("checkuserma")) { Main.checkMaUsers(messageMa.split(" ")[1]); }}catch(Exception e) {}
		}
		
		String[] args = messageMa.split(" ");
		for(int i = 0 ; i < args.length;i++) {
			if(args[i].equals("addma")) { Main.addBotToChannel(user); }
			if(args[i].equals("removema")) { Main.removeBotFromChannel(user);}
			if(args[i].equals("setdelayma") && i!= args.length-1) { Main.setDelay(user,args[i+1]); }
			if(args[i].equals("setratioma") && i!= args.length-1) { Main.setRatio(user,args[i+1]); }
			
			else {
				for(String s: helpMessage) {
					//this.whisper(user, s);
				}
				return;
			}
		}	
	}

	//FIXME: ADD "I barely know her"
	public void onMessage(User user, Channel channelMa, String messageMa) {
		
		if(channelMa.toString().equals("#mabotlmao")) { onWhisper(user,messageMa); return; }
		
		if(!Main.readyToSendMessage(channelMa)){ return; }
		
		int indexMa;
		String newMessageMa;
		for(String triggerMa : triggers) {
			indexMa = messageMa.toLowerCase().lastIndexOf(triggerMa);
			if(indexMa != -1 
					&& !(indexMa == 0 && messageMa.length() != triggerMa.length()) 
					&& (indexMa-1 < 0 || messageMa.charAt(indexMa-1)==' ' || indexMa+triggerMa.length() >= messageMa.length() || messageMa.charAt(indexMa+triggerMa.length())==' ' ) 
			) {
				if(!Main.dueToPost(channelMa)) { return; }
				
				lmaoadd:
				if(true) {
					newMessageMa = messageMa.substring(0,indexMa) + " " + messageMa.substring(indexMa,indexMa + triggerMa.length());
					if(triggerMa.equals("candice")) { newMessageMa = newMessageMa.substring(0,newMessageMa.length()-4) + " "+"dice dick fit in ya mouth"; break lmaoadd;}
					if(triggerMa.equals("dixon")) { newMessageMa = newMessageMa.substring(0,newMessageMa.length()-2) + " "+"on ya face"; break lmaoadd;}
					if(triggerMa.equals("saw")) { newMessageMa += " con"; }
					if(triggerMa.equals("bofa") || triggerMa.equals("dragon") || triggerMa.equals("sucon") || triggerMa.equals("saw") || triggerMa.equals("sawcon")){ newMessageMa += " dese"; }
					if(!triggerMa.equals("joe")) { newMessageMa += " balls"; }
					else { newMessageMa += " mama"; }
					if(triggerMa.equals("dragon")) { newMessageMa += " on ya face"; }
				}
				
				newMessageMa += " lmao";
				this.sendMessage(newMessageMa,channelMa);
				return;
			}
		}
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
