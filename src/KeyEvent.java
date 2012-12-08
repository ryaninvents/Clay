
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
public class KeyEvent extends Event {
	
	public int code;
	
    public static final int VK_ENTER          = '\n';
    public static final int VK_BACK_SPACE     = '\b';
    public static final int VK_SHIFT          = 0x10;
	public static final int VK_ESCAPE 		  = 0x1B;
    public static final int VK_SPACE     	  = 0x20;
    public static final int VK_PERIOD         = 0x2E;
    public static final int VK_SLASH          = 0x2F;
    public static final int VK_0              = 0x30;
    public static final int VK_1              = 0x31;
    public static final int VK_2              = 0x32;
    public static final int VK_3              = 0x33;
    public static final int VK_4              = 0x34;
    public static final int VK_5              = 0x35;
    public static final int VK_6              = 0x36;
    public static final int VK_7              = 0x37;
    public static final int VK_8              = 0x38;
    public static final int VK_9              = 0x39;
    public static final int VK_A              = 0x41;
    public static final int VK_B              = 0x42;
    public static final int VK_C              = 0x43;
    public static final int VK_D              = 0x44;
    public static final int VK_E              = 0x45;
    public static final int VK_F              = 0x46;
    public static final int VK_G              = 0x47;
    public static final int VK_H              = 0x48;
    public static final int VK_I              = 0x49;
    public static final int VK_J              = 0x4A;
    public static final int VK_K              = 0x4B;
    public static final int VK_L              = 0x4C;
    public static final int VK_M              = 0x4D;
    public static final int VK_N              = 0x4E;
    public static final int VK_O              = 0x4F;
    public static final int VK_P              = 0x50;
    public static final int VK_Q              = 0x51;
    public static final int VK_R              = 0x52;
    public static final int VK_S              = 0x53;
    public static final int VK_T              = 0x54;
    public static final int VK_U              = 0x55;
    public static final int VK_V              = 0x56;
    public static final int VK_W              = 0x57;
    public static final int VK_X              = 0x58;
    public static final int VK_Y              = 0x59;
    public static final int VK_Z              = 0x5A;
	
	public KeyEvent(EventType type, long when, int code) 
	{
		super(type, when);
		this.code = code;
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
