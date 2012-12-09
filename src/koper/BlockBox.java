package koper;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.LinkedList;


public class BlockBox extends AbstractBox
{

	List<LineBox> lineBoxList = new LinkedList<LineBox>();
	List<AbstractBox> boxList = new LinkedList<AbstractBox>();
	LineBox curLineBox = null; 
	
	public BlockBox(int width)
	{
		this(width, 0);
	}
	
	public BlockBox(int width, int height)
	{
		super();
		bounds.width = width;
		bounds.height = height;
		margin.top = 5;
		margin.bottom = 5;
		margin.left = 5;
		margin.right = 5;
		curLineBox = new LineBox(bounds.width - margin.left - margin.right);
		lineBoxList.add(curLineBox);
		
	}
	
	public void addBox(BlockBox childBox)
	{
		if (Configuration.debugRendering)
			System.out.println("BlockBox.addBox");
		if (curLineBox.inlineBoxList.size() > 0)
		{
			curLineBox = new LineBox(bounds.width - margin.left - margin.right);
			lineBoxList.add(curLineBox);
		}
		curLineBox.addBlockBox(childBox);
		boxList.add(childBox);
	}

	public void addInlineBox(InlineElementBox childBox)
	{
		if (Configuration.debugRendering)
			System.out.println("BlockBox.addInlineBox");
		curLineBox.addInlineBox(childBox);
		boxList.add(childBox);
	}

	public AbstractBox getBoxAt(int x, int y)
	{
		AbstractBox hitBox = null;
		
		for (AbstractBox aBox : lineBoxList)
		{
			hitBox = aBox.getBoxAt(x, y);
			if (hitBox != null)
				break;
		}
		return hitBox;
	}
	
	public void render(RenderedPages rp)
	{
		if (Configuration.debugRendering)
			System.out.println("BlockBox.render");
		List<AbstractBox> curlineBoxList = new LinkedList<AbstractBox>();
		int lineHeight = 0;
		int whitespaceCount = 0;
		
		int tx = rp.margin.left;
//		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D gg = rp.getGraphics();
		for (AbstractBox aBox : boxList)
		{
			if (tx + aBox.getPreferredSize(gg).width < bounds.width - rp.margin.right)
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
			else
			{
				// render current line
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
					// render line on page
					for (AbstractBox aaBox : curlineBoxList)
					{
						aaBox.setPos(rp.tx + tx1 / resolution, rp.ty);
						aaBox.render(rp);
						tx1 = tx1 + aaBox.getPreferredSize(gg).width * resolution;
						if (aaBox instanceof WhitespaceBox)
						{
							tx1 = tx1 + delta;
							aaBox.resizeWidth(delta);
						}
					}
					// prepare new line for layout
					whitespaceCount = 0;
					rp.ty = rp.ty + lineHeight;
					// check if last line on page
					if (rp.ty > rp.getPageHeight() - rp.margin.bottom - lineHeight)
					{
						rp.newPage();
						gg = rp.getGraphics();
					}

					lineHeight = 0;
					tx = rp.margin.left;
//					System.out.println("new Line");
					curlineBoxList.clear();
					if (!(curlineBoxList.isEmpty() && (aBox instanceof WhitespaceBox)))
						// no whitespace at the beginning of a line
					{
						curlineBoxList.add(aBox);
						tx = tx + aBox.getPreferredSize(gg).width;
//						System.out.printf("add curlineBox %s width: %d\n", aBox.getClass().toString(), aBox.getPreferredSize(gg).width);
					}
				}
			}
			if (rp.ty > rp.getPageHeight() - rp.margin.bottom - lineHeight)
			{
				rp.newPage();
				gg = rp.getGraphics();
			}
		}
		// render last line
		tx = 0;
		for (AbstractBox aaBox : curlineBoxList)
		{
			aaBox.setPos(rp.tx + tx, rp.ty);
			aaBox.render(rp);
			tx = tx + aaBox.getPreferredSize(gg).width;
			if (aaBox.getHeight() > lineHeight)
				lineHeight = aaBox.getHeight();
			
		}
		rp.ty = rp.ty + lineHeight;
		if (rp.ty > rp.getPageHeight() - rp.margin.bottom - lineHeight)
		{
			rp.newPage();
			gg = rp.getGraphics();
			tx = 0;
		}

//		if (ty + lineHeight > bounds.height)
//			bounds.height = ty + lineHeight;
		
	}
	
	@Override
	public void layout(Graphics2D gg)
	{
		// TODO Auto-generated method stub
//		gg.translate(marginLeft, marginTop);
//		curLineBox.layout(gg);
		int ty = margin.top;
		int tx = margin.left;
		int lineboxCount = 0;
//		System.out.printf("number of boxes: %d\n", lineBoxList.size());
		for (AbstractBox aBox : lineBoxList)
		{
			lineboxCount = lineboxCount + 1;
//			System.out.printf("Start BlockBox.layout count: %d y:%d\n", lineboxCount, ty);
			gg.translate(tx, ty);
			aBox.setPos(bounds.x + tx, bounds.y + ty);
			aBox.layout(gg);
			gg.translate(-tx, -ty);
			ty = ty + aBox.getHeight() + margin.bottom;
//			System.out.printf("End BlockBox.layout: lineheight: %d ty:%d\n", aBox.getHeight(), ty);
			if (ty > bounds.height)
				bounds.height = ty;
		}
		drawBorders(gg, Color.red);
	}
	
}
