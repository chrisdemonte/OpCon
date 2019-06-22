
public class ProbabilityCalculator {

	ProbabilityCalculator (){
	}
	
	public int probResponse (int code) {
		switch (code) {
		case 1:
			return 100;
		case 2:
			return 75;
		case 3:
			return 50;
		case 4:
			return 75;
		case 5:
			return 50;
		case 6:
			return 25;
		case 7:
			return 50;
		case 8:
			return 25;
		case 9:
			return 0;
		default:
			return 0;
		}
	}
	public int probNoResponse (int code) {
		switch (code) {
		case 1:
			return 50;
		case 2:
			return 25;
		case 3:
			return 0;
		case 4:
			return 75;
		case 5:
			return 50;
		case 6:
			return 25;
		case 7:
			return 100;
		case 8:
			return 75;
		case 9:
			return 50;
		default:
			return 0;
		}
		
	}
}
/**

1. Is the trial length and rest length in seconds? MS? Can this information be presented on screen? Like this:

Trial length (ms.)
Rest length (ms.)

2. What is the rest length? Is that inter-trial interval? If so, can we just call it that?

3. Can you list the probability codes that are programmed in there? Otherwise I'll have to have RA's memorize them/write them down.

4. This is more of a question: Between trials, the program seems to countdown twice. I'm assuming the first is the ITI and the second is during the period when the participant can press the button? 

5. When the window is maximized (which I would want to do, so the desktop isn't a distraction), the window shifts to the left of the screen. Can we have it display in the middle of the screen when maximized? 
**/