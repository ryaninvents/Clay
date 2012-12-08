import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;


public class EPub 
{
	String tmpDir = Configuration.getDirectory() + File.separator + "TEMP/";
	HashMap<String, String> contentMap = new HashMap<String, String>();
	protected LinkedList<Chapter> chapters = new LinkedList<Chapter>();
	protected String title = "";
	protected String date = "";
	protected String author = "";
	protected String coverFilename = "";
	private String cover = "";
	BufferedImage coverimage = null;

	public EPub(String filename) throws FileNotFoundException
	{
		
		FileInputStream fis = null;
		System.out.println("EPub: " + filename);
		filename = filename.endsWith(".epub") ? filename : filename + ".epub";
//			java.net.URL url = this.getClass().getResource(filename );
//			java.net.URI uri = url.
//			System.out.println("EPub: filename: " + filename + " url: " + url);
		fis = new FileInputStream(new File(Configuration.getDirectory() + File.separator + filename));
//			in = url.openStream();
		ZipInputStream zin = new ZipInputStream(new BufferedInputStream(fis));
		ZipEntry ze = null;
		try {
			File tmp = new File(tmpDir);
			if (!tmp.exists())
				tmp.mkdir();
			
			while ((ze = zin.getNextEntry()) != null) {
//				if (ze.getName().endsWith("/"))
				if (ze.isDirectory())
				{
					tmp = new File(tmpDir + ze.getName());
					tmp.mkdirs();
					System.out.println("Creating directory " + tmpDir + ze.getName());
				}
				else
				{
					int size;
					byte[] buffer = new byte[2048];
					tmp = new File(tmpDir + ze.getName());
					File dir = new File(tmp.getParent());
					System.out.println("Parent " + dir.getAbsolutePath());
					if (!dir.exists())
					{
						dir.mkdirs();
						System.out.println("Creating directory " + dir);
					}
					System.out.println("Unzipping " + ze.getName() + " to " + tmpDir + ze.getName());
					FileOutputStream fos = new FileOutputStream(tmpDir + ze.getName());
					BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);

					while ((size = zin.read(buffer, 0, buffer.length)) != -1) 
					{
						bos.write(buffer, 0, size);
					}
					bos.flush();
					bos.close();
				}
				zin.closeEntry();
				
			}
			zin.close();
			fis.close();
//			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readContainerXml();
		
	}
	
	protected void readContainerXml()
	{
		System.out.println("readContainerXml");
    	try 
    	{
			Source source = new Source(new FileInputStream(Configuration.getDirectory() + File.separator + "TEMP/META-INF/container.xml"));
	        net.htmlparser.jericho.Element rootfileElem = source.getFirstElement("rootfile");
        	String fullPath = rootfileElem.getAttributeValue("full-path");
	        System.out.println(fullPath);
	        if (!fullPath.isEmpty())
	        	readContentOpf(fullPath);

		} 
    	catch (FileNotFoundException e) 
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
	
	protected void readContentOpf(String filename)
	{
		System.out.println("readContentOpf");
    	try 
    	{
    		File file = new File(tmpDir + filename);
			Source source = new Source(new FileInputStream(file));
	        net.htmlparser.jericho.Element metadataElem = source.getFirstElement("metadata");
	        title = metadataElem.getFirstElement("dc:title").getContent().toString();
	        Element dateElem = metadataElem.getFirstElement("dc:date");
	        if (dateElem != null)
	        {
	        	date = dateElem.getContent().toString();
	        }
	        author = metadataElem.getFirstElement("dc:creator").getContent().toString();
	        List<Element> metaElemList = metadataElem.getAllElements("meta");
	        for (net.htmlparser.jericho.Element element : metaElemList) 
	        {
	        	String content = element.getAttributeValue("content");
	        	String name = element.getAttributeValue("name");
	        	System.out.println("meta: " + content + " -- " + name);
	        	if (name.equalsIgnoreCase("cover"))
	        		cover = content;
	        		
	        }
	        
	        net.htmlparser.jericho.Element manifestElem = source.getFirstElement("manifest");
	        List<net.htmlparser.jericho.Element> elementList=manifestElem.getAllElements("item");
	        for (net.htmlparser.jericho.Element element : elementList) 
	        {
	        	String hRef = element.getAttributeValue("href");
	        	String id = element.getAttributeValue("id");
	        	System.out.println(hRef + " -- " + id);
	        	if (id.equalsIgnoreCase(cover))
	        	{
	        		coverFilename = hRef;
		        	System.out.println("Coverpage : " + file.getParent() + File.separator + coverFilename);
		        	coverimage = ImageIO.read(new File(file.getParent() + File.separator + coverFilename));
	        	}
	        	else
	        		contentMap.put(id, hRef);
	        		
	        }
	    	readTocNcx(file.getParent() + "/" + contentMap.get("ncx"));
		} 
    	catch (FileNotFoundException e) 
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
	
	protected void readTocNcx(String filename)
	{
		System.out.println("readTocNcx");
    	try 
    	{
			Source source = new Source(new FileInputStream(filename));
	        net.htmlparser.jericho.Element manifestElem = source.getFirstElement("navmap");
	        List<net.htmlparser.jericho.Element> elementList=manifestElem.getAllElements("navpoint");
	        for (net.htmlparser.jericho.Element element : elementList) 
	        {
		        net.htmlparser.jericho.Element navlabelElem = element.getFirstElement("navlabel");
		        net.htmlparser.jericho.Element textElem = navlabelElem.getFirstElement("text");
	        	String label = textElem.getContent().toString();
	        	net.htmlparser.jericho.Element contentElem = element.getFirstElement("content");
	        	String sourceRef = contentElem.getAttributeValue("src"); 
	        	System.out.println(label + " -- " + sourceRef);
	        	chapters.add(new Chapter(label, sourceRef));
	        }
		} 
    	catch (FileNotFoundException e) 
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
	
	
	public LinkedList<Chapter> getChapterList()
	{
		return chapters;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public void process()
	{
		
	}

	public BufferedImage getCoverImage() 
	{
		return coverimage;
	}
	
	
}
