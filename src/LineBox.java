import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;


public class LineBox extends AbstractBox
{

	List<AbstractBox> inlineBoxList = new LinkedList<AbstractBox>();
	
	public LineBox(int width)
	{
		bounds.width = width;
	}
	
	public void addInlineBox(InlineElementBox ieb)
	{
		inlineBoxList.add(ieb);
	}
	
	public void addBlockBox(BlockBox bb)
	{
		inlineBoxList.add(bb);
	}
	
	public AbstractBox getBoxAt(int x, int y)
	{
		AbstractBox hitBox = null;
		
		for (AbstractBox aBox : inlineBoxList)
		{
			hitBox = aBox.getBoxAt(x, y);
			if (hitBox != null)
				break;
		}
		return hitBox;
	}
	

	@Override
	public void layout(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		List<AbstractBox> curlineBoxList = new LinkedList<AbstractBox>();
		int tx = 0;
		int ty = 0;
		int lineHeight = 0;
		int whitespaceCount = 0;
//		System.out.printf("number of lines: %d\n", inlineBoxList.size());
//		System.out.printf("Start LineBox.layout:  width: %d, height: %d\n", tx, lineHeight);
		for (AbstractBox aBox : inlineBoxList)
		{
//			System.out.printf("tx: %d, width: %d\n", tx, bounds.width);
			if (tx + aBox.getPreferredSize(gg).width > bounds.width)
			{
				if (curlineBoxList.isEmpty()) 
				{
					// single Box width is exceeding line width
					curlineBoxList.add(aBox);
					if (aBox.getPreferredSize(gg).height > lineHeight)
						lineHeight = aBox.getPreferredSize(gg).height;
				}
				else
				{
					// more than one box in the current line
					if (curlineBoxList.get(curlineBoxList.size()-1) instanceof WhitespaceBox)
					{
						// no whitespace at end of line
						curlineBoxList.remove(curlineBoxList.size()-1);
						whitespaceCount = whitespaceCount - 1;
						
					}
					// justify current line
//					System.out.println("Begin new Line");
//					System.out.printf("size of curlineBoxList: %d\n", curlineBoxList.size());
					int tx1 = 0;
					int delta = 0;
					int resolution = 288;
					if (whitespaceCount > 0)
						delta = (resolution *(bounds.width - tx)) / whitespaceCount;
					for (AbstractBox aaBox : curlineBoxList)
					{
						gg.translate(tx1 / resolution, ty);
						aaBox.setPos(bounds.x + tx1 / resolution, bounds.y + ty);
						aaBox.layout(gg);
						gg.translate(-tx1 / resolution, -ty);
						tx1 = tx1 + aaBox.getPreferredSize(gg).width * resolution;
						if (aaBox instanceof WhitespaceBox)
						{
							tx1 = tx1 + delta;
							aaBox.resizeWidth(delta);
						}
					}
					// prepare new line for layout
					whitespaceCount = 0;
					ty = ty + lineHeight;
					lineHeight = 0;
					tx = 0;
//					System.out.println("new Line");
					curlineBoxList.clear();
					if (!(curlineBoxList.isEmpty() && (aBox instanceof WhitespaceBox)))
						// no whitespace at the beginning of a line
					{
						curlineBoxList.add(aBox);
						tx = aBox.getPreferredSize(gg).width;
//						System.out.printf("add curlineBox %s width: %d\n", aBox.getClass().toString(), aBox.getPreferredSize(gg).width);
					}
				}
			}
			else
			{
				// add box to current line
				if (!(curlineBoxList.isEmpty() && (aBox instanceof WhitespaceBox)))
				{
					// no whitespace at the beginning of a line
					if (aBox instanceof WhitespaceBox)
						whitespaceCount = whitespaceCount + 1;
					curlineBoxList.add(aBox);
//					System.out.printf("add curlineBox %s width: %d\n", aBox.getClass().toString(), aBox.getPreferredSize(gg).width);
					if (aBox.getPreferredSize(gg).height > lineHeight)
						lineHeight = aBox.getPreferredSize(gg).height;
					tx = tx + aBox.getPreferredSize(gg).width;
				}

			}
//			System.out.printf("End LineBox.layout:  width: %d, height: %d\n", tx, lineHeight);
		}
		tx = 0;
		for (AbstractBox aaBox : curlineBoxList)
		{
			gg.translate(tx, ty);
			aaBox.setPos(bounds.x + tx, bounds.y + ty);
			aaBox.layout(gg);
			gg.translate(-tx, -ty);
			tx = tx + aaBox.getPreferredSize(gg).width;
			if (aaBox.getHeight() > lineHeight)
				lineHeight = aaBox.getHeight();
			
		}
//		System.out.printf("End LineBox.layout:  width: %d, height: %d\n", tx, lineHeight);
		if (ty + lineHeight > bounds.height)
			bounds.height = ty + lineHeight;
		drawBorders(gg, Color.green);
	}
	
	
	
}
