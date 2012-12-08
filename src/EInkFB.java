/*
 *  Copyright (c) 2012 Michael Zucchi
 *
 *  This file is part of ReaderZ, a Java e-ink reader application.
 *
 *  ReaderZ is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ReaderZ is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ReaderZ.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Interface to e-ink framebuffer
 * @author notzed
 */
public class EInkFB
{

	static final boolean debug = true;
	ByteBuffer p;
	final int width;
	final int height;
	final int stride;
	BufferedImage image;
	Graphics2D gg;

	ByteBuffer buffer;
	int updateid = 10;
	// update flags
	public final static int UPDATE_INVERT = 1;
	public final static int UPDATE_MONOCHROME = 2;
	public final static int UPDATE_WAIT = 0x80;
	public final static int UPDATE_NOCOPY = 0x40;
	// update mode
	public final static int UPDATE_MODE_PARTIAL = 0;
	public final static int UPDATE_MODE_FULL = 1;
	// ioctl set values
	public static final int MXCFB_SET_TEMPERATURE = 0;
	public static final int MXCFB_SET_AUTO_UPDATE_MODE = 1;
	public static final int MXCFB_WAIT_FOR_UPDATE_COMPLETE = 2;
	public static final int MXCFB_SET_PWRDOWN_DELAY = 3;
	public static final int MXCFB_SET_UPDATE_SCHEME = 4;
	public static final int MXCFB_SET_BORDER_MODE = 5;
	public static final int MXCFB_SET_EPD_PWR0_CTRL = 6;
	public static final int MXCFB_SET_EPD_PWR2_CTRL = 7;
	public static final int MXCFB_SET_TEMP_AUTO_UPDATE_PERIOD = 8;
	public static final int MXCFB_SET_MERGE_ON_WAVEFORM_MISMATCH = 9;
	// ioctl SET_AUTO_UPDATE_MODE values
	public static final int AUTO_UPDATE_MODE_REGION_MODE = 0;
	public static final int AUTO_UPDATE_MODE_AUTOMATIC_MODE = 1;
	// ioctl SET_UPDATE_SCHEME values
	public static final int UPDATE_SCHEME_SNAPSHOT = 0;
	public static final int UPDATE_SCHEME_QUEUE = 1;
	public static final int UPDATE_SCHEME_QUEUE_AND_MERGE = 2;

	static native ByteBuffer open(String name);

	static native ByteBuffer getBuffer(ByteBuffer p);

	static native int getWidth(ByteBuffer p);

	static native int getHeight(ByteBuffer p);

	static native int getVirtualWidth(ByteBuffer p);

	static native int getVirtualHeight(ByteBuffer p);

	/**
	 * 
	 * @param p
	 * @param mode partial=0, full=1
	 * @param l
	 * @param t
	 * @param w
	 * @param h
	 * @param flags 
	 */
	static native void update(ByteBuffer p, int mode, int marker, int l, int t, int w, int h, int flags);

	static native int ioctlSetInteger(ByteBuffer p, int code, int value);

	static native void close(ByteBuffer p);

	static {
    	System.out.println(System.getProperty("java.library.path"));
//    	System.out.println(Configuration.getDirectory() + File.separator + "einkfb");
		System.loadLibrary("einkfb");
	}

	public EInkFB(String name) throws IOException {
    	System.out.println("construct EInkFB");
		p = open(name);
		if (p == null) {
			throw new IOException("Unable to open framebuffer device");
		}

		buffer = getBuffer(p).order(ByteOrder.nativeOrder());
		width = getWidth(p);
		height = getHeight(p);
		stride = getVirtualWidth(p);

		// We request 8 bit greyscale data during init
		image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage src = getImage();

		gg = src.createGraphics();
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gg.setColor(Color.white);
		gg.fillRect(0, 0, getWidth(), getHeight());
		gg.setBackground(Color.white);
		gg.setColor(Color.black);

		ioctlSetInteger(p, MXCFB_SET_AUTO_UPDATE_MODE, AUTO_UPDATE_MODE_REGION_MODE);
		ioctlSetInteger(p, MXCFB_SET_UPDATE_SCHEME, UPDATE_SCHEME_QUEUE_AND_MERGE);
	}

	public void close() {
		close(p);
		p = null;
	}

	
	/**
	 * Retrieve framebuffer itself.
	 * Format depends on framebuffer.
	 * @return 
	 */
	public ByteBuffer getBuffer() {
		return getBuffer(p).order(ByteOrder.nativeOrder());
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BufferedImage getImage() {
		return image;
	}

	int clamp(int v, int min, int max) {
		return Math.max(Math.min(v, max), min);
	}

	private void updateFrame(int l, int r, int t, int b) {
		int w = r - l;

		long now = System.currentTimeMillis();

		byte[] src = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

		for (int y = t; y <= b; y++) {
			buffer.position(l + y * stride);
			buffer.put(src, l + y * width, w);
		}
		buffer.rewind();

		now = System.currentTimeMillis() - now;
		System.out.printf(" update byte-byte region took %d.%03ds\n", now / 1000, now % 1000);
	}

	/**
	 * Update the framebuffer from the backing image,
	 * and force the e-ink to refresh.
	 * 
	 * @param rect region to refresh
	 * @param flags UPDATE_* flags
	 */
	public void update(int marker, Rectangle rect, int flags) {

		// copy region to buffered image

		int l = rect.x;
		int t = rect.y;
		int r = l + rect.width;
		int b = t + rect.height;


		l = clamp(l, 0, width - 1);
		r = clamp(r, 0, width - 1);
		t = clamp(t, 0, height - 1);
		b = clamp(b, 0, height - 1);

		if (l == r && t == b) {
			return;
		}

		if (debug) {
			System.out.printf("#### update %d,%d %d,%d flags=%02x\n", l, t, r, b, flags);
		}

		updateFrame(l, r, t, b);
		long now = System.currentTimeMillis();

		update(p, UPDATE_MODE_PARTIAL, marker, l, t, r - l, b - t, flags);
		now = System.currentTimeMillis() - now;
		System.out.printf(" update partial took %d.%03ds\n", now / 1000, now % 1000);
	}

	public void update(Rectangle rect, int flags) {
		update(updateid++, rect, flags);
	}

	/**
	 * Update whole image
	 * @param flags 
	 */
	public void update(int marker, int flags) {

		if (debug) {
			System.out.printf("#### update full %d,%d %d,%d flags=%02x\n", 0, 0, width, height, flags);
		}

		if ((flags & UPDATE_NOCOPY) == 0) {
			updateFrame(0, 0, width, height);
		}
		long now = System.currentTimeMillis();

		update(p, UPDATE_MODE_FULL, marker, 0, 0, width, height, flags);
		now = System.currentTimeMillis() - now;
		System.out.printf(" update full took %d.%03ds\n", now / 1000, now % 1000);
	}

	public void update(int flags) {
		update(2, flags);
	}

	@SuppressWarnings("unused")
	public void shakeit() {
		if (true) {
			// this takes about 0.8s, assuming the previous update was a monochrome one
			update(4, UPDATE_WAIT);
		} else if (true) {
			// this takes about 1s to update, although it flashes black
			//buffer.rewind();
			//for (int i = 0, len = buffer.remaining(); i < len; i++) {
			//	buffer.put((short) 0xffff);
			//}
			//buffer.rewind();
			//update(p, UPDATE_MODE_FULL, 3, 0, 0, width, height, 0);
			update(4, UPDATE_WAIT);
		} else {
			// this takes about 1.3s
			update(3, EInkFB.UPDATE_INVERT);
			update(4, UPDATE_WAIT);
		}
	}

	public void waitUpdate(int marker) {
		ioctlSetInteger(p, MXCFB_WAIT_FOR_UPDATE_COMPLETE, marker);
	}

	public void waitUpdate() {
		waitUpdate(2);
	}

	
	public void start()
	{
		while (true)
		{
			try
			{
				Thread.sleep(100);
				
			}
			catch(InterruptedException ie)
			{
				System.out.println("Thread-Error!");
			}
			
		}

	}
	
}

