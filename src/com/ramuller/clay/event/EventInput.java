package com.ramuller.clay.event;

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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Base class for reading /dev/input/event*
 * 
 * @author notzed
 */
public abstract class EventInput extends Thread {
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
	private ArrayList<EventListener> eventListeners = new ArrayList<EventListener>();

	public EventInput(String path) throws IOException {
		event = Files.newByteChannel(Paths.get(path), EnumSet.of(StandardOpenOption.READ));
//		this.source = source;
	}

	public void addEventListener(EventListener el) {
		eventListeners.add(el);
	}

	public void removeEventListener(EventListener el) {
		eventListeners.remove(el);
	}

	void event(Event ev) {
		for (int i = 0; i < eventListeners.size(); i++) {
			try {
				eventListeners.get(i).event(ev);
			} catch (Throwable t) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, t);
			}
		}
	}

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

	abstract public Event readEvent() throws IOException;

	@Override
	public void run() {
		try {
			while (true) {
				Event ev = readEvent();

				if (ev != null) {
					event(ev);
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}
}
