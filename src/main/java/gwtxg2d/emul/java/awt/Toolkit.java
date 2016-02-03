package java.awt;

/**
 * This class is the abstract superclass of all actual
 * implementations of the Abstract Window Toolkit. Subclasses of
 * the <code>Toolkit</code> class are used to bind the various components
 * to particular native toolkit implementations.
 * <p>
 * Many GUI events may be delivered to user
 * asynchronously, if the opposite is not specified explicitly.
 * As well as
 * many GUI operations may be performed asynchronously.
 * This fact means that if the state of a component is set, and then
 * the state immediately queried, the returned value may not yet
 * reflect the requested change.  This behavior includes, but is not
 * limited to:
 * <ul>
 * <li>Scrolling to a specified position.
 * <br>For example, calling <code>ScrollPane.setScrollPosition</code>
 *     and then <code>getScrollPosition</code> may return an incorrect
 *     value if the original request has not yet been processed.
 *
 * <li>Moving the focus from one component to another.
 * <br>For more information, see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html#transferTiming">Timing
 * Focus Transfers</a>, a section in
 * <a href="http://java.sun.com/docs/books/tutorial/uiswing/">The Swing
 * Tutorial</a>.
 *
 * <li>Making a top-level container visible.
 * <br>Calling <code>setVisible(true)</code> on a <code>Window</code>,
 *     <code>Frame</code> or <code>Dialog</code> may occur
 *     asynchronously.
 *
 * <li>Setting the size or location of a top-level container.
 * <br>Calls to <code>setSize</code>, <code>setBounds</code> or
 *     <code>setLocation</code> on a <code>Window</code>,
 *     <code>Frame</code> or <code>Dialog</code> are forwarded
 *     to the underlying window management system and may be
 *     ignored or modified.  See {@link java.awt.Window} for
 *     more information.
 * </ul>
 * <p>
 * Most applications should not call any of the methods in this
 * class directly. The methods defined by <code>Toolkit</code> are
 * the "glue" that joins the platform-independent classes in the
 * <code>java.awt</code> package with their counterparts in
 * <code>java.awt.peer</code>. Some methods defined by
 * <code>Toolkit</code> query the native operating system directly.
 *
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @author      Fred Ecks
 * @since       JDK1.0
 */
public abstract class Toolkit {

    @Deprecated
    public abstract String[] getFontList();

    @Deprecated
    public abstract FontMetrics getFontMetrics(Font font);

    /**
     * The default toolkit.
     */
    private static Toolkit toolkit;


    public static synchronized Toolkit getDefaultToolkit() {
        if (toolkit == null) {
            toolkit = new Toolkit() {
				
				@Override
				public FontMetrics getFontMetrics(Font font) {
					return null;
				}
				
				@Override
				public String[] getFontList() {
					return new String[0];
				}
			};			
        }
        return toolkit;
    }
}
