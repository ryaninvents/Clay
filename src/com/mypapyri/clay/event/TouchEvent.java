package com.mypapyri.clay.event;

import java.awt.event.MouseEvent;

import com.mypapyri.clay.ClaySystem;


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
public class TouchEvent extends MouseEvent {
	private static final long serialVersionUID = 2425002140064710527L;
	
	public static final int DRAG = 1<<8;
	public static final int MOUSE_LONG_CLICKED = 1<<9;
	
	private int type;
	
	public TouchEvent(long when, int x, int y, int type) 
	{
		super(ClaySystem.getActiveApp(),type,when,0,x,y,x,y,1,false,BUTTON1);
		this.type = type;
	}
	
	public TouchEvent toLandscape(){
		return new TouchEvent(this.getWhen(),ClaySystem.getScreenWidth()-this.getY(),this.getX(), this.getType());
	}

	public int getType() {
		return type;
	}
}
