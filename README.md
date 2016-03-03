Gwtx-G2D
========
Graphics2D emulation on the HTML canvas for GWT.

It's based on earlier work at:
https://code.google.com/archive/p/gwt-awt/
where the CanvasGraphics is now a real implementation of Graphics2D

Since it's contains some the OpenJDK sources, it carries the same license.

Supports
--------
* All Shapes like java.awt.Rectangle (2D)
* calculations inside shapes (like intersects, contains, etc)
* CanvasGraphics.transformedPoint(...) to calculate canvas pixel locations to transformed location
* Graphics.create() and Graphics.dispose()
* Direct drawing on a HTML5-Canvas

Why use Graphics2D on a Canvas
-------------
* Graphics2D API is almost identical to Canvas context
* Code using Graphics2D API will be able to draw to various outputs, like PDF, SVG, rich-client GUI (AWT, swing,SWT), 
	image formats (PNG, JPEg, etc) and now Canvas as well.  

Not supported
-------------
* Various lesser used (getter) functions on the Graphics object

Known issues
-------------
* Font metrics should use canvas context.measureText 

Usage
-----
Add the following to your pom.xml in dependencies
 
	<dependency>
		<groupId>nl.loxia.gwt</groupId>
		<artifactId>gwtx-g2d</artifactId>
		<version>0.7.0-SNAPSHOT</version>
	</dependency> 

Add the following to your .gwt.xml 

	<inherits name="gwtxg2d.GwtxG2D" />
	
Example
-------
	private Dimension size;
	private Canvas canvas;
	private CanvasGraphics g;
	
	public void onModuleLoad() {
		size = new Dimension(Window.getClientWidth(), (int) (Window.getClientHeight()/1.3));
		canvas = Canvas.createIfSupported();
		RootPanel.get().add(canvas);
		canvas.setPixelSize(size.width, size.height);
		canvas.setCoordinateSpaceWidth(size.width);
		canvas.setCoordinateSpaceHeight(size.height);
		g = new CanvasGraphics(canvas.getContext2d());
		redraw();
	}
	
	private void redraw() {
		// Clear
		Context2d context = g.getContext2d();
		context.save();
		// Use the identity matrix while clearing the canvas
		context.setTransform(1, 0, 0, 1, 0, 0);
		context.clearRect(0, 0, size.width, size.height);
		context.restore();
		
		
		
		// Create some Shapes 
		Rectangle r = new Rectangle(10, 10, 30, 30); 
		Path2D path = new Path2D.Double(); 
		path.moveTo(10, 10); 
		path.curveTo(10, 0, 40, 0, 40, 10);
		
		// Draw it on the Canvas 
		g.setColor(java.awt.Color.red); 
		g.draw(r); 
		g.setColor(java.awt.Color.blue); 
		g.fill(path);
		
		
		// Use the Graphics2D object in some Awt/Swing panel code
		//panel.paintComponent(g);
	}
