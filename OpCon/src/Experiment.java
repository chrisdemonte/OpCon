import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class Experiment {
	
	String patientID;
	int numberOfGames;
	String dateCreated;
	ArrayList<Game> gameList;
	String[] instructions;
	
	Experiment (String ID, int num){
		patientID = ID;
		numberOfGames = num;	
		Date date = new Date();
		dateCreated = date.toString();
		gameList = new ArrayList<Game>();
		
		instructions = new String[5];
		instructions[0] = "\tImagine the following scenario. You are a scientist and you are setting up the apparatus for your latest experiment. The apparatus includes a light bulb wired up to a light switch button and has its own power supply. It is very important for the experiment that you, the scientist, feel that you can control when the light is switched on or remains off. You are working on a tight budget and had to house an old power supply provided by another researcher. You are slightly worried that the power supply that you had to use may not be suitable for this purpose. Therefore, you want to test the apparatus to assess how much control you have over the light switching on. ";
		instructions[1] = "\tAt the beginning of the test you will see a button on the screen. While the button is on the screen you will be able to press it and see whether the light switches on or remains off. The button will become green to register that you've clicked it successfully. You can press the light button using the left mouse button. If the light switches on, it will stay on for 5 seconds before switching off. The button will then disappear from the screen and will re-appear again when you can press the button again. In the test, there will be many opportunities to press the button and see what happens. ";
		instructions[2] = "\tIn order to judge how much control your button pressing has over whether the light comes on, you need to know what happens when you press the button. \n\tIT IS ALSO VERY IMPORTANT that you know what happens when you do not press the button, so on about half of the button pressing opportunities, you should sit back and see what happens when you don't press the button. ";
		instructions[3] = "\tIf you press the button below, you will see an example of the light coming on. When you have done that press the \"Forward\" button to proceed. ";
		instructions[4] = ("\tAt the end of the test, you will be asked to make a judgment about how much control your pressing the button had over whether the light came on. \n" + 
				"\t\"Total control\" means that the light switching is completely determined by your choice of response- either pressing or not pressing the button. \n" + 
				"\t\"No control\" means that your button pressing had no influence at all on whether the light was switched on or not. In other words, the light switching on had nothing to do with what you did or didn't do. \n" + 
				"\t\"Partial control\" means that you pressing or not pressing the button did influence the light switching on, but not completely. In other words, whether you pressed or didn't press the button mattered to some extent, but not totally. \n" + 
				"\tIf you have any questions about the test, please ask the experimenter now:");
	}
	
	public void setID (String ID) {
		patientID = ID;
	}
	public String getID () {
		return patientID;
	}
	public void setNumberOfGames (int num) {
		numberOfGames = num;
	}
	public int getNumberOfGames () {
		return numberOfGames;
	}
	public String getDate () {
		return dateCreated;
	}
	public ArrayList<Game> getGameList (){
		return gameList;
	}
	public String[] getInstructions () {
		return instructions;
	}
	public void finalCalc () {
		for (int i = 0; i < gameList.size(); i++) {
			gameList.get(i).calcDeltaP();
		}
	}
	
	public String toString() {
		String spacer = "\n*************************************";
		String str = (spacer + "\nUser ID: " + patientID + "\n" + dateCreated + spacer);
		
		for (int i = 0; i < gameList.size(); i++) {
			str += (spacer + "\n\t\tGame " + (i+1) + spacer + "\n" + gameList.get(i).toString());
			str += spacer;
		}
		return str;
	}
	
	public void writeToFile (String data, String fileName) {
		String file = (fileName + ".txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(data);
			bw.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
