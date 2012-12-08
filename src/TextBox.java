import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;


public class TextBox extends InlineElementBox
{

	String text = "";
	Font font = null;
	static Font defaultFont = new Font("Arial", Font.PLAIN, 18);
	boolean highlight = false;
	
	public TextBox(String text)
	{
		this.text = text;
		this.font = defaultFont;
	}
	
	public TextBox(String text, Font font)
	{
		this.text = text;
		this.font = font;
	}
	
	@Override
	public Dimension getPreferredSize(Graphics2D gg)
	{
		gg.setFont(font);
		return new Dimension(gg.getFontMetrics().stringWidth(text), gg.getFontMetrics().getHeight());
	}
	
	public void render(RenderedPages rp)
	{
		rp.getGraphics().setFont(font);
		rp.getGraphics().drawString(text, bounds.x, bounds.y + rp.getGraphics().getFontMetrics().getHeight());
		bounds.width = rp.getGraphics().getFontMetrics().stringWidth(text);
		if (rp.getGraphics().getFontMetrics().getHeight() > bounds.height)
			bounds.height = rp.getGraphics().getFontMetrics().getHeight();
		drawBorders(rp.getGraphics(), Color.blue);
		if (Configuration.debugRendering)
			System.out.printf("TextBox.layout: #" + text + "# height:%d\n", bounds.height);
	}
	
	@Override
	public void layout(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		gg.setFont(font);
		gg.drawString(text, 0, gg.getFontMetrics().getHeight());
		bounds.width = gg.getFontMetrics().stringWidth(text);
		if (gg.getFontMetrics().getHeight() > bounds.height)
			bounds.height = gg.getFontMetrics().getHeight();
		drawBorders(gg, Color.blue);
		System.out.printf("TextBox.layout: #" + text + "# height:%d\n", bounds.height);
	}
	
	
}
