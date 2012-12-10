package com.ramuller.sumerian.input;


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



/**
 *
 * @author notzed
 */
public class MouseEvent extends Event {
	public int x;
	public int y;
	
	public MouseEvent(MouseEvent me)
	{
		this(me.type, me.when, me.x, me.y);
	}
	
	public MouseEvent(EventType type, long when, int x, int y) 
	{
		super(type, when);
		this.x = x;
		this.y = y;
	}
	
	public String describe(){
		return "Mouse " + x + "," + y;
	}
	
//	public Point relativeTo(Gadget g) {
//		int rx = x;
//		int ry = y;
//		
//		while (g != null) {
//			rx -= g.bounds.x;
//			ry -= g.bounds.y;
//			g = g.parent;
//		}
//		
//		return new Point(rx, ry);
//	}
}
