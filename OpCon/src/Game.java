import java.util.ArrayList;

public class Game {

	int numberOfTrials;
	int trialLength;
	int restLength;
	int sliderValue;
	int probCode;
	double A;
	double B;
	double C;
	double D;
	double deltaP;
	
	ArrayList<Trial> trialList;
	
	Game (){
		this(0,1,1,1);
	}
	Game (int num, int length, int rest, int prob){
		numberOfTrials = num;
		trialLength = length;
		restLength = rest;
		probCode = prob;
		A = 0;
		B = 0;
		C = 0;
		D = 0;
		deltaP = 0.0;
		trialList = new ArrayList<Trial>();
		for (int i = 0; i < num; i++) {
			trialList.add(new Trial());
		}
	}
	
	public void setNumberOfTrials (int num) {
		numberOfTrials = num;
	}
	public int getNumberOfTrials () {
		return numberOfTrials;
	}
	public void setTrialLength (int num) {
		trialLength= num;
	}
	public int getTrialLength () {
		return trialLength/1000;
	}
	public void setRestLength (int num) {
		restLength = num;
	}
	public int getRestLength () {
		return restLength/1000;
	}
	public void setProbCode (int num) {
		probCode = num;
	}
	public int getProbCode () {
		return probCode;
	}
	public void setSliderValue (double val) {
		sliderValue = (int)val;
	}
	public ArrayList<Trial> getTrialList (){
		return trialList;
	}
	public void calcDeltaP () {
		for (int i = 0; i < trialList.size(); i++) {
			if (trialList.get(i).getClicked() == 1 && trialList.get(i).getLight() == 0) {
				B++;
			}
			if (trialList.get(i).getClicked() == 1 && trialList.get(i).getLight() == 1) {
				A++;
			}
			if (trialList.get(i).getClicked() == 0 && trialList.get(i).getLight() == 0) {
				D++;
			}
			if (trialList.get(i).getClicked() == 0 && trialList.get(i).getLight() == 1) {
				C++;
			}
		}

		if ( (A == 0.0 && B == 0.0)) {
			B = 1;
			deltaP = ((A/(A + B)) - (C/(C + D))) * 10.0;
			B = 0;
		}
		else if ( (C == 0.0 && D == 0.0)) {
			D = 1;
			deltaP = ((A/(A + B)) - (C/(C + D))) * 10.0;
			D = 0;
		}
		else {
			deltaP = ((A/(A + B)) - (C/(C + D))) * 10.0;
		}
		
	}
	public String toString () {
		String str = ("Trial Length (ms): " + trialLength + "\r\nInter-Trial Interval (ms): " + restLength + "\r\nSlider Value: " + sliderValue + "\r\nDelta P = "+ ((Math.round(deltaP * 1000.0))/100.0)+ "\r\nA = " + (int)A + "\tB = " + (int)B + "\r\nC = " + (int)C + "\tD = " + (int)D + "\nContingency: ");
		switch (probCode) {
		case 1:
			str += "100/50";
			break;
		case 2:
			str += "75/25";
			break;
		case 3:
			str += "50/0";
			break;
		case 4:
			str += "75/75";
			break;
		case 5:
			str += "50/50";
			break;
		case 6:
			str += "25/25";
			break;
		case 7:
			str += "50/100";
			break;
		case 8:
			str += "25/75";
			break;
		case 9:
			str += "0/50";
			break;
		default:
			str += "error was made entering code";
		}
		str+="\r\n*************************************";
		for (int i = 0; i < trialList.size(); i++) {
			str += ("\r\nTrial " + (i+1) + "\r\n" + trialList.get(i).toString());
		}
		return str;
	}
}
