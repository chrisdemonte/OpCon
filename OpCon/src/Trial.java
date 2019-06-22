
public class Trial {

	int clicked = 0;
	int light = 0;
	
	Trial (){
		
	}
	public void setClicked () {
		clicked = 1;
	}
	public int getClicked () {
		return clicked;
	}
	public void setLight () {
		light = 1;
	}
	public int getLight () {
		return light;
	}
	public String toString () {
		String str = "\t";
		if (clicked == 1) {
			str += "clicked\t\t";
		}
		else {
			str += "not clicked\t";
		}
		if (light == 1) {
			str += "light on";
		}
		else {
			str += "light off";
		}
		return str;
	}
}
