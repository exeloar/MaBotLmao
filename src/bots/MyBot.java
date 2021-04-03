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
	
	final String[] triggers = {"ma", "dese", "dragon", "sucon","sawcon", "saw", "joe"};
	Random random = new Random();
	
	//FIXME: ADD "I barely know her"
	public void onMessage(User user, Channel channelMa, String messageMa) {
		
		try {
			long cooldownMa = Main.getCooldownMaAmount(channelMa);
			if( !(cooldownMa == -1) && Math.abs(System.currentTimeMillis() - Main.getLastMaClock(channelMa)) < cooldownMa) { return; } 
		}
		catch(Exception e) { System.out.println(e.getMessage()); }
		
		try{ Main.setLastMaClock(channelMa); } catch(Exception e) { System.out.println(e.getMessage()); }
		
		int indexMa;
		String newMessageMa;
		for(String triggerMa : triggers) {
			indexMa = messageMa.toLowerCase().lastIndexOf(triggerMa);
			if(indexMa != -1 
					&& !(indexMa == 0 && messageMa.length() != triggerMa.length()) 
					&& (indexMa-1 < 0 || messageMa.charAt(indexMa-1)==' ' || indexMa+triggerMa.length() >= messageMa.length() || messageMa.charAt(indexMa+triggerMa.length())==' ' ) 
			) {
				try{ if(random.nextInt(Main.getProbabilityMaAmount(channelMa)) != 0) { return; } } catch(Exception e) { System.out.println(e.getMessage()); }
		
				newMessageMa = messageMa.substring(0,indexMa) + " " + messageMa.substring(indexMa,indexMa + triggerMa.length());
				if(triggerMa.equals("saw")) { newMessageMa += " con"; }
				if(triggerMa.equals("dragon") || triggerMa.equals("sucon") || triggerMa.equals("saw") || triggerMa.equals("sawcon")){ newMessageMa += " dese"; }
				if(!triggerMa.equals("joe")) { newMessageMa += " balls"; }
				else { newMessageMa += " mama"; }
				if(triggerMa.equals("dragon")) { newMessageMa += " on ya face"; }
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
