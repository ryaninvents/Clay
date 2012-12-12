package com.ramuller.clay.event;

import com.ramuller.clay.ui.Component;


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
public class TouchEvent extends Event {
	public int x;
	public int y;
	
	public TouchEvent(TouchEvent me)
	{
		this(me.getType(), me.getWhen(), me.x, me.y);
	}
	
	public TouchEvent(EventType type, long when, int x, int y) 
	{
		super(type, when);
		this.x = x;
		this.y = y;
	}
	
	public String describe(){
		return this.getType().name()+" " + x + "," + y;
	}
	
	public TouchEvent dup(int x,int y){
		return new TouchEvent(this.getType(),this.getWhen(),this.x-x,this.y-y);
	}

	public TouchEvent dup(Component c) {
		return dup(c.getX(),c.getY());
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
