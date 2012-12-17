package com.mypapyri.clay.event;

import java.awt.event.MouseEvent;

import com.mypapyri.clay.ClaySystemSettings;
import com.ramuller.clay.event.EventType;
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
public class TouchEvent extends MouseEvent {
	private static final long serialVersionUID = 2425002140064710527L;
	
	public int x;
	public int y;
	
	public TouchEvent(long when, int x, int y) 
	{
		super(null,0,when,0,x,y,1,false);
		this.x = x;
		this.y = y;
	}
	
	public TouchEvent toPortrait(){
		return new TouchEvent(this.getWhen(),ClaySystemSettings.getScreenWidth()-this.y,this.x);
	}
}
