package gwtxg2d.client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.google.gwt.canvas.dom.client.Context2d.LineJoin;

/**
 * Graphics2D replacement for GWT
 * 
 * Works as a Wrapper around the HTML5 Context2d element
 */
public class CanvasGraphics extends Graphics2D {
	private final Context2d context;

	public CanvasGraphics(Context2d context) {
		this.context = context;
		trackTransforms(context);
	}

	public Context2d getContext2d() {
		return context;
	}

	@Override
	public void translate(int x, int y) {
		context.translate(x, y);
	}

	private String convert(Color color, float alpha) {
		return "rgba(" + color.getRed() + "," + color.getGreen() + ","
				+ color.getBlue() + ", " + alpha + ")";
	}

	@Override
	public void setColor(Color color) {
		if (color != null) {
			float alpha = color.getAlpha() / 255f;
			String colorString = convert(color, alpha);
			context.setStrokeStyle(colorString);
			context.setFillStyle(colorString);
		}
	}

	@Override
	public void setStroke(Stroke s) {
		if (s instanceof BasicStroke) {
			BasicStroke stroke = (BasicStroke) s;
			// TODO support for stroke pattern as soon ie is support stroke patterns
			if (stroke.getDashArray() != null) {
				System.err.println("Ignoring stroke pattern. They aren't suppoted yet");
				// throw new
				// IllegalArgumentException("Stroke patterns aren't supported");
			}

			context.setMiterLimit(stroke.getMiterLimit());
			context.setLineWidth(stroke.getLineWidth());

			switch (stroke.getLineJoin()) {
			case BasicStroke.JOIN_BEVEL:
				context.setLineJoin(LineJoin.BEVEL);
				break;
			case BasicStroke.JOIN_MITER:
				context.setLineJoin(LineJoin.MITER);
				break;
			case BasicStroke.JOIN_ROUND:
				context.setLineJoin(LineJoin.ROUND);
				break;
			default:
				System.err.println("unknown line join type");
				throw new IllegalArgumentException("illegal line join value");
			}

			switch (stroke.getEndCap()) {
			case BasicStroke.CAP_BUTT:
				context.setLineCap(LineCap.BUTT);
				break;
			case BasicStroke.CAP_ROUND:
				context.setLineCap(LineCap.ROUND);
				break;
			case BasicStroke.CAP_SQUARE:
				context.setLineCap(LineCap.SQUARE);
				break;
			default:
				System.err.println("unknown line cap type");
				throw new IllegalArgumentException("illegal line cap value");
			}
		}
	}

	@Override
	public void draw(Shape shape) {
		if (shape != null) {
			path(shape);
			context.stroke();
		} else {
			System.err.println("dreckdreckdreck");
		}
	}

	@Override
	public void fill(Shape shape) {
		if (shape != null) {
			path(shape);
			context.fill();
		}
	}

	private void path(Shape shape) {
		path(shape.getPathIterator(null));
	}

	private void path(PathIterator i) {
		float[] coords = new float[6];
		context.beginPath();
		while (!i.isDone()) {
			int segment = i.currentSegment(coords);
			if (segment == PathIterator.SEG_MOVETO) {
				context.moveTo(coords[0], coords[1]);
			} else if (segment == PathIterator.SEG_LINETO) {
				context.lineTo(coords[0], coords[1]);
			} else if (segment == PathIterator.SEG_QUADTO) {
				context.quadraticCurveTo(coords[0], coords[1], coords[2],
						coords[3]);
			} else if (segment == PathIterator.SEG_CUBICTO) {
				context.bezierCurveTo(coords[0], coords[1], coords[2],
						coords[3], coords[4], coords[5]);
			} else if (segment == PathIterator.SEG_CLOSE) {
				context.closePath();
			} else {
				throw new RuntimeException("Unknown Segment " + segment);
			}
			i.next();
		}
	}

	@Override
	public void setFont(Font font) {
		if (font != null && font.getFontName() != null)
			context.setFont(font.getFontName());
	}

	@Override
	public void clipRect(int x, int y, int width, int height) {
		// TODO validate, intersec, null
		this.setClip(x, y, width, height);
	}

	@Override
	public void setClip(int x, int y, int width, int height) {
		context.beginPath();
		context.moveTo(x, y);
		context.lineTo(x + width, y);
		context.lineTo(x + width, y + height);
		context.lineTo(x, y + height);
		context.lineTo(x, y);
		context.closePath();
		context.clip();
	}

	@Override
	public void setClip(Shape clip) {
		path(clip);
		context.clip();
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		context.clearRect(x, y, width, height);
	}

	@Override
	public void drawString(String str, int x, int y) {
		context.strokeText(str, x, y);
	}

	@Override
	public void drawChars(char data[], int offset, int length, int x, int y) {
		drawString(new String(data, offset, length), x, y);
	}

	@Override
	public void drawLine(int x, int y, int x2, int y2) {
		context.beginPath();
		context.moveTo(x, y);
		context.lineTo(x2, y2);
		context.stroke();
	}

	@Override
	public void drawString(String str, float x, float y) {
		context.strokeText(str, x, y);
	}

	@Override
	public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addRenderingHints(Map<?, ?> hints) {
		// TODO Auto-generated method stub
	}

	@Override
	public void translate(double tx, double ty) {
		context.translate(tx, ty);
	}

	@Override
	public void rotate(double theta) {
		context.rotate(theta);
	}

	@Override
	public void rotate(double theta, double x, double y) {
		translate(x, y);
        rotate(theta);
        translate(-x, -y);
	}

	@Override
	public void scale(double sx, double sy) {
		context.scale(sx, sy);
	}

	@Override
	public void shear(double shx, double shy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void transform(AffineTransform at) {
		context.transform(at.getScaleX(), at.getShearX(), at.getShearY(), at.getScaleY(), at.getTranslateX(), at.getTranslateY());
	}

	@Override
	public void setTransform(AffineTransform at) {
		// TODO check if correct
		context.setTransform(at.getScaleX(), at.getShearX(), at.getShearY(), at.getScaleY(), at.getTranslateX(), at.getTranslateY());
	}

	@Override
	public AffineTransform getTransform() {
		// TODO improve
		return new AffineTransform();
	}

	@Override
	public void setBackground(Color color) {
		// TODO Auto-generated method stub
	}

	@Override
	public Color getBackground() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stroke getStroke() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clip(Shape shape) {
		if (shape != null) {
			path(shape);
			context.clip();
		}
	}

	@Override
	public Graphics create() {
		context.save();
		return this;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPaintMode() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setXORMode(Color c1) {
		// TODO Auto-generated method stub
	}

	@Override
	public Font getFont() {
		return Font.decode(context.getFont());
	}

	@Override
	public FontMetrics getFontMetrics(Font f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getClipBounds() {
		// TODO Auto-generated method stub
		return new Rectangle();
	}

	@Override
	public Shape getClip() {
		// TODO Auto-generated method stub
		return new Rectangle();
	}

	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		fill(new Rectangle(x, y, width, height));
	}

	@Override
	public void drawRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		draw(new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeight));
	}

	@Override
	public void fillRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		fill(new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeight));
	}

	@Override
	public void drawOval(int x, int y, int w, int h) {
		draw(new Ellipse2D.Double(x, y, w, h));
	}

	@Override
	public void fillOval(int x, int y, int w, int h) {
		fill(new Ellipse2D.Double(x, y, w, h));
	}

	@Override
	public void drawArc(int x, int y, int w, int h, int startAngle,
			int arcAngle) {
		draw(new Arc2D.Double(x, y, w, h, startAngle, arcAngle, Arc2D.OPEN));
	}

	@Override
	public void fillArc(int x, int y, int w, int h, int startAngle,
			int arcAngle) {
		fill(new Arc2D.Double(x, y, w, h, startAngle, arcAngle, Arc2D.OPEN));
	}

	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
         context.beginPath();
         context.moveTo(xPoints[0], yPoints[0]);
         for (int i = 0; i < nPoints; i++) {
        	 context.lineTo(xPoints[i], yPoints[i]);
         }
         context.closePath();
         context.stroke();
	}

	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		draw(new Polygon(xPoints, yPoints, nPoints));
	}

	@Override
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		fill(new Polygon(xPoints, yPoints, nPoints));
	}

	@Override
	public void dispose() {
		context.restore();
	}

	@Override
	public void setRenderingHint(Key hintKey, Object hintValue) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getRenderingHint(Key hintKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRenderingHints(Map<?, ?> hints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RenderingHints getRenderingHints() {
		// TODO Auto-generated method stub
		return new RenderingHints(null);
	}
	

	public Point transformedPoint(int x , int y) {
		return transformedPoint(context,x,y);
	}
	
	private native Point transformedPoint(Context2d ctx, int x , int y) /*-{
		var pt = ctx.transformedPoint(x,y);
		return @java.awt.Point::new(II)(pt.x,pt.y);
	}-*/;
	
	private native void trackTransforms(Context2d ctx) /*-{
		// Adds ctx.getTransform() - returns an SVGMatrix
		// Adds ctx.transformedPoint(x,y) - returns an SVGPoint

		var svg = $doc.createElementNS("http://www.w3.org/2000/svg",'svg');
		var xform = svg.createSVGMatrix();
		ctx.getTransform = function(){ return xform; };
		
		var savedTransforms = [];
		var save = ctx.save;
		ctx.save = function(){
			savedTransforms.push(xform.translate(0,0));
			return save.call(ctx);
		};
		var restore = ctx.restore;
		ctx.restore = function(){
			xform = savedTransforms.pop();
			return restore.call(ctx);
		};
	
		var scale = ctx.scale;
		ctx.scale = function(sx,sy){
			xform = xform.scaleNonUniform(sx,sy);
			return scale.call(ctx,sx,sy);
		};
		var rotate = ctx.rotate;
		ctx.rotate = function(radians){
			xform = xform.rotate(radians*180/Math.PI);
			return rotate.call(ctx,radians);
		};
		var translate = ctx.translate;
		ctx.translate = function(dx,dy){
			xform = xform.translate(dx,dy);
			return translate.call(ctx,dx,dy);
		};
		var transform = ctx.transform;
		ctx.transform = function(a,b,c,d,e,f){
			var m2 = svg.createSVGMatrix();
			m2.a=a; m2.b=b; m2.c=c; m2.d=d; m2.e=e; m2.f=f;
			xform = xform.multiply(m2);
			return transform.call(ctx,a,b,c,d,e,f);
		};
		var setTransform = ctx.setTransform;
		ctx.setTransform = function(a,b,c,d,e,f){
			xform.a = a;
			xform.b = b;
			xform.c = c;
			xform.d = d;
			xform.e = e;
			xform.f = f;
			return setTransform.call(ctx,a,b,c,d,e,f);
		};
		var pt  = svg.createSVGPoint();
		ctx.transformedPoint = function(x,y){
			pt.x=x; pt.y=y;
			return pt.matrixTransform(xform.inverse());
		}
	}-*/;
}
