package com.ramuller.clay.ui.containers;

import com.ramuller.clay.ui.Container;
/**
 * Implements an absolute layout, where component (x,y)
 * positions must be specified.
 * @author ryan
 *
 */
/*
 * Actually, there's really not much to do here since
 * the original Container class implements so much of
 * the code already. The only thing that we need here
 * is to create a concrete class we can instantiate.
 */
public class AbsoluteLayout extends Container {

	public void reflow() {}

}
