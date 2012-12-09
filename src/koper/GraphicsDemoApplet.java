package koper;



public class GraphicsDemoApplet extends Applet {

	@Override
	public void event(KeyEvent ev) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		setIcon("graphicsdemo.png");
		setMainPanel(new GraphicsDemoPanel());

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GraphicsDemoApplet";
	}

}
