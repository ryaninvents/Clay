package com.mypapyri.clay.event;

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

import java.awt.AWTEvent;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Base class for reading /dev/input/event*
 * 
 * @author notzed
 */
public abstract class EventInput<XEvent extends AWTEvent,XListener extends EventListener> extends Thread {
	// fields from linux/input.h:input_event

	long time_s;
	long time_us;
	int type;
	int code;
	int value;
	//
	private ReadableByteChannel event;
	private ByteBuffer data = ByteBuffer.allocate(16).order(ByteOrder.nativeOrder());
//	protected Display source;
	private ArrayList<XListener> eventListeners = new ArrayList<XListener>();
	
	public EventInput(){

	}
	
	protected void setReadableByteChannel(String path) throws IOException{
		
		event=Files.newByteChannel(Paths.get(path), EnumSet.of(StandardOpenOption.READ));
	}

	public void addListener(XListener el) {
		eventListeners.add(el);
	}

	public void removeListener(XListener el) {
		eventListeners.remove(el);
	}
/*
	void event(Event ev) {
		for (int i = 0; i < eventListeners.size(); i++) {
			try {
				eventListeners.get(i).event(ev);
			} catch (Throwable t) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, t);
			}
		}
	}*/
	public abstract void fireEvent(XEvent ev);

	/**
	 * Read next event part into fields, blocking if necessary.
	 * @return the type
	 * @throws IOException 
	 */
	public int readRawEvent() throws IOException {
		int len = 0;
		int res;

		data.rewind();

		do {
			res = event.read(data);
			if (res >= 0) {
				len += res;
			}
		} while (len < 16);

		data.flip();

		time_s = data.getInt() & 0xffffffffL;
		time_us = data.getInt() & 0xffffffffL;

		type = data.getShort();
		code = data.getShort();
		value = data.getInt();

//		System.out.printf("** Raw Event %d  type=%d code=%d value=%d\n", time_s * 1000 + time_us / 1000, type, code, value);
		
		return type;
	}

	protected ArrayList<XListener> getListeners(){
		return eventListeners;
	}
	
	abstract public void readEvent() throws IOException;

	@Override
	public void run() {
		try {
			while (true) {
				readEvent();
			}
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}
}
