package koper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import net.htmlparser.jericho.Source;


public class Chapter 
{
	String name = null;
	String reference = null;
	String teaser = null;

	public Chapter(String name, String reference)
	{
		this.name = name;
		this.reference = reference;
		readTeaser();
	}

	public void readTeaser()
	{
		Source source = null;
		FileInputStream fis = null; 
		if (reference.indexOf("#") == -1)
		{
		try
		{
			//	        	source = new Source(new FileInputStream("testnews.xhtml"));
			//	        	source = new Source(new FileInputStream("Der_Schatz_im_Silbersee_1_kurz.xhtml"));
			fis = new FileInputStream(Configuration.getDirectory() + File.separator + "TEMP/OEBPS/" + reference);
			source = new Source(fis);
			net.htmlparser.jericho.Element elemTeaser = source.getFirstElement("h2");
//			net.htmlparser.jericho.Tag tag2 = bodyElem.getFirstStartTag();
			if (elemTeaser != null)
				teaser = elemTeaser.getContent().toString();
			else 
				teaser = "";
			fis.close();
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

}
