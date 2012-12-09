package koper;
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


/**
 * Read the keyboard input on the kobo.
 * @author notzed
 */
public class KoboKeyInput extends EventInput {
	/*
	 * Input driver version is 1.0.0
	Input device ID: bus 0x19 vendor 0x0 product 0x0 version 0x0
	Input device name: "mxckpd"
	Supported events:
	Event type 0 (Sync)
	Event type 1 (Key)
	Event code 0 (Reserved)
	Event code 28 (Enter)
	Event code 59 (F1)
	Event code 60 (F2)
	Event code 61 (F3)
	Event code 62 (F4)
	Event code 65 (F7)
	Event code 66 (F8)
	Event code 102 (Home)
	Event code 103 (Up)
	Event code 105 (Left)
	Event code 106 (Right)
	Event code 108 (Down)
	Event code 116 (Power)
	
	 On the kobo touch, one only has power and home.
	 */
	// current state
	public int xpos = 0;
	public int ypos = 0;
	int press = 0; // not sure exactly what this does
	public int touch;
	//
	boolean mouse1 = false;

	public KoboKeyInput(String path) throws IOException {
		super(path);
	}

	@Override
	public Event readEvent() throws IOException {
		while (readRawEvent() != 1) {
		}

//		return new KeyEvent(value == 1 ? EventType.KeyPressed : EventType.KeyReleased, time_s * 1000 + time_us/1000, code);
		return new KeyEvent(value == 1 ? EventType.HomeButton : EventType.KeyReleased, time_s * 1000 + time_us/1000, code);
	}
}
