package koper;



public class SketchApplet extends Applet implements PluginFunction
{

	@Override
	public void event(KeyEvent ev) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		setIcon("sketch_icon.jpg");
		setMainPanel(new SketchPanel());

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SketchApplet";
	}

	@Override
	public void setParameter(int param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getResult() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return "Sketch plugin";
	}

	@Override
	public boolean hasError() {
		// TODO Auto-generated method stub
		return false;
	}

}
