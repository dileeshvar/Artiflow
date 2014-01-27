package edu.ssn.sase.artiflow.MailManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ArtiflowMailHandler {
	private Map<String, String> serverDetailsMap = new HashMap<String, String>();
	
	public void sendMailNow(List<String> mailIds, String projectName, String configFilePath) {
		try {
			getMailServerParametersFromConfigFile(configFilePath);
			new NotifyActors(serverDetailsMap, mailIds, projectName).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getMailServerParametersFromConfigFile(String configFilePath) throws IOException {
		File f = new File(configFilePath);
		FileInputStream fis = new FileInputStream(f);
		Properties properties = new Properties();
		properties.load(fis);
		Set<Object> keys = properties.keySet();
		for(Object key : keys)
		{				
			String st = key.toString();
			String value = properties.getProperty((String) key);
			serverDetailsMap.put(st, value);
		}
	}
	
	/*public static void main(String args[]) {
		ArtiflowMailHandler hdlr = new ArtiflowMailHandler();
		hdlr.sendMailNow(null);
	}*/
}
