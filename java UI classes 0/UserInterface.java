package <define yourself>;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;



public class UserInterface {
	
	
		/*****************************************************************************************************
		 * Simple class for representing a point in a 2 dimensional space with 2 floats.
		 *  "amplitude" means "Math.sqrt(x*x+y*y)",
		 *  "minus" means "-x, -y", "inverse" means "1.f/x, 1.f/y",
		 *  "mirrorto" gets symmetry, "mirrored" generetes a new point which is symmetric,
		 *  "increase" increases current point, "sumwith" make a new point which is the sum,
		 * 	"amplifie" multiplies the current point, "multiplie" returns a new point which is multiplication.
		 *****************************************************************************************************/
		static public class LBpoint2f {
			public float x, y; public LBpoint2f() { x=0.f;y=0.f; }
			public LBpoint2f(double x, double y) { this.x=(float)x;this.y=(float)y; }
			public LBpoint2f(LBpoint2f o) { x=o.x;y=o.y; }
			public LBpoint2f(double o) { x=(float)o;y=(float)o; }
			public LBpoint2f(LBpoint2i o) { x=o.x;y=o.y; }
			public LBpoint2f(LBpoint2d o) { x=(float)o.x;y=(float)o.y; }
			public double getAmplitude() { return Math.sqrt(x*x+y*y); }
			public LBpoint2f setAmplitude(double amplitude) { amplifie(amplitude/getAmplitude()); return this; }
			public LBpoint2f disAmplitude() { double a = getAmplitude(); x/=a;y/=a; return this; }
			public LBpoint2f minus() { return new LBpoint2f(-x,-y); }
			public LBpoint2f inverse() { return new LBpoint2f(1/x,1/y); }
			public LBpoint2i toInt() { return new LBpoint2i(this); }
			public LBpoint2d toDouble() { return new LBpoint2d(this); }
			public LBpoint2f absolute() { x=x<0.f ? -x:x; y=y<0.f ? -y:y; return this; }
			@Override public String toString() { return x+"|"+y; }
			@Override public boolean equals(Object other) { return x==((LBpoint2f)other).x && y==((LBpoint2f)other).y; }
			@Override protected Object clone() { return new LBpoint2f(this); }
			
			public LBpoint2f mirrorto(LBpoint2f o) { x=o.x*2-x;y=o.y*2-y; return this; }
			public LBpoint2f mirrorto(double ox, double oy) { x=(float)ox*2-x;y=(float)oy*2-y; return this; }
			public LBpoint2f mirroredto(LBpoint2f o) { return new LBpoint2f(o.x*2-x,o.y*2-y); }
			public LBpoint2f mirroredto(double ox, double oy) { return new LBpoint2f(ox*2-x,oy*2-y); }
			public double distanceto(LBpoint2f o) { float dx=x-o.x, dy=y-o.y; return Math.sqrt(dx*dx+dy*dy); }
			public double distanceto(double ox, double oy) { float dx=(float)(x-ox), dy=(float)(y-oy); return Math.sqrt(dx*dx+dy*dy); }
			
			public LBpoint2f copy(LBpoint2f o) { x=o.x;y=o.y; return this; }
			public LBpoint2f copy(LBpoint2i o) { x=o.x;y=o.y; return this; }
			public LBpoint2f copy(LBpoint2d o) { x=(float) o.x;y=(float) o.y; return this; }
			public LBpoint2f copy(double ox, double oy) { x=(float)ox;y=(float)oy; return this; }
			public LBpoint2f copy(double o) { x=(float)o;y=(float)o; return this; }
			
			public LBpoint2f increase(LBpoint2f o) { x+=o.x;y+=o.y; return this; }
			public LBpoint2f sumwith(LBpoint2f o) { return new LBpoint2f(x+o.x,y+o.y); }
			public LBpoint2f amplifie(LBpoint2f o) { x*=o.x;y*=o.y; return this; }
			public LBpoint2f multiplie(LBpoint2f o) { return new LBpoint2f(x*o.x,y*o.y); }
			public LBpoint2f increase(double ox, double oy) { x+=ox;y+=oy; return this; }
			public LBpoint2f sumwith(double ox, double oy) { return new LBpoint2f(x+ox,y+oy); }
			public LBpoint2f amplifie(double ox, double oy) { x*=ox;y*=oy; return this; }
			public LBpoint2f multiplie(double ox, double oy) { return new LBpoint2f(x*ox,y*oy); }
			public LBpoint2f increase(double o) { x+=o;y+=o; return this; }
			public LBpoint2f sumwith(double o) { return new LBpoint2f(x+o,y+o); }
			public LBpoint2f amplifie(double o) { x*=o;y*=o; return this; }
			public LBpoint2f multiplie(double o) { return new LBpoint2f(x*o,y*o); }
			
		}
		/*****************************************************************************************************
		 * Simple class for representing a point in a 2 dimensional space with 2 doubles.
		 *  "amplitude" means "Math.sqrt(x*x+y*y)",
		 *  "minus" means "-x, -y", "inverse" means "1./x, 1./y",
		 *  "mirrorto" gets symmetry, "mirrored" generetes a new point which is symmetric,
		 *  "increase" increases current point, "sumwith" make a new point which is the sum,
		 * 	"amplifie" multiplies the current point, "multiplie" returns a new point which is multiplication.
		 *****************************************************************************************************/
		static public class LBpoint2d {
			public double x, y; public LBpoint2d() { x=0.f;y=0.f; }
			public LBpoint2d(double x, double y) { this.x=(double)x;this.y=(double)y; }
			public LBpoint2d(LBpoint2d o) { x=o.x;y=o.y; }
			public LBpoint2d(double o) { x=(double)o;y=(double)o; }
			public LBpoint2d(LBpoint2i o) { x=o.x;y=o.y; }
			public LBpoint2d(LBpoint2f o) { x=o.x;y=o.y; }
			public double getAmplitude() { return Math.sqrt(x*x+y*y); }
			public LBpoint2d setAmplitude(double amplitude) { amplifie(amplitude/getAmplitude()); return this; }
			public LBpoint2d disAmplitude() { double a = getAmplitude(); x/=a;y/=a; return this; }
			public LBpoint2d minus() { return new LBpoint2d(-x,-y); }
			public LBpoint2d inverse() { return new LBpoint2d(1/x,1/y); }
			public LBpoint2i toInt() { return new LBpoint2i(this); }
			public LBpoint2f toFloat() { return new LBpoint2f(this); }
			public LBpoint2d absolute() { x=x<0.f ? -x:x; y=y<0.f ? -y:y; return this; }
			@Override public String toString() { return x+"|"+y; }
			@Override public boolean equals(Object other) { return x==((LBpoint2d)other).x && y==((LBpoint2d)other).y; }
			@Override protected Object clone() { return new LBpoint2d(this); }
			
			public LBpoint2d mirrorto(LBpoint2d o) { x=o.x*2-x;y=o.y*2-y; return this; }
			public LBpoint2d mirrorto(double ox, double oy) { x=(double)ox*2-x;y=(double)oy*2-y; return this; }
			public LBpoint2d mirroredto(LBpoint2d o) { return new LBpoint2d(o.x*2-x,o.y*2-y); }
			public LBpoint2d mirroredto(double ox, double oy) { return new LBpoint2d(ox*2-x,oy*2-y); }
			public double distanceto(LBpoint2d o) { double dx=x-o.x, dy=y-o.y; return Math.sqrt(dx*dx+dy*dy); }
			public double distanceto(double ox, double oy) { double dx=(double)(x-ox), dy=(double)(y-oy); return Math.sqrt(dx*dx+dy*dy); }
			
			public LBpoint2d copy(LBpoint2f o) { x=o.x;y=o.y; return this; }
			public LBpoint2d copy(LBpoint2i o) { x=o.x;y=o.y; return this; }
			public LBpoint2d copy(LBpoint2d o) { x=o.x;y=o.y; return this; }
			public LBpoint2d copy(double ox, double oy) { x=(double)ox;y=(double)oy; return this; }
			public LBpoint2d copy(double o) { x=(double)o;y=(double)o; return this; }
			
			public LBpoint2d increase(LBpoint2d o) { x+=o.x;y+=o.y; return this; }
			public LBpoint2d sumwith(LBpoint2d o) { return new LBpoint2d(x+o.x,y+o.y); }
			public LBpoint2d amplifie(LBpoint2d o) { x*=o.x;y*=o.y; return this; }
			public LBpoint2d multiplie(LBpoint2d o) { return new LBpoint2d(x*o.x,y*o.y); }
			public LBpoint2d increase(double ox, double oy) { x+=ox;y+=oy; return this; }
			public LBpoint2d sumwith(double ox, double oy) { return new LBpoint2d(x+ox,y+oy); }
			public LBpoint2d amplifie(double ox, double oy) { x*=ox;y*=oy; return this; }
			public LBpoint2d multiplie(double ox, double oy) { return new LBpoint2d(x*ox,y*oy); }
			public LBpoint2d increase(double o) { x+=o;y+=o; return this; }
			public LBpoint2d sumwith(double o) { return new LBpoint2d(x+o,y+o); }
			public LBpoint2d amplifie(double o) { x*=o;y*=o; return this; }
			public LBpoint2d multiplie(double o) { return new LBpoint2d(x*o,y*o); }
		}
		/*****************************************************************************************************
		 * Simple class for representing a point in a 2 dimensional space with 2 ints.
		 *  "amplitude" means "Math.sqrt(x*x+y*y)",
		 *  "minus" means "-x, -y", "inverse" means "1.f/x, 1.f/y",
		 *  "mirrorto" gets symmetry, "mirrored" generetes a new point which is symmetric,
		 *  "increase" increases current point, "sumwith" make a new point which is the sum,
		 * 	"amplifie" multiplies the current point, "multiplie" returns a new point which is multiplication.
		 *****************************************************************************************************/
		static public class LBpoint2i {
		    public int x, y; public LBpoint2i() { x = 0; y = 0; }
		    public LBpoint2i(double x, double y) { this.x = (int)x;this.y = (int)y; }
		    public LBpoint2i(LBpoint2i o) { x = o.x; y = o.y; }
		    public LBpoint2i(double o) { x = (int)o; y = (int)o; }
		    public LBpoint2i(LBpoint2f o) { x = (int)o.x; y = (int)o.y; }
		    public LBpoint2i(LBpoint2d o) { x = (int)o.x; y = (int)o.y; }
			public double getAmplitude() { return Math.sqrt(x*x+y*y); }
		    public LBpoint2i setAmplitude(double amplitude) { amplifie(amplitude/getAmplitude()); return this; }
		    public LBpoint2i disAmplitude() { double a = getAmplitude(); x/=a; y/=a; return this; }
		    public LBpoint2i minus() { return new LBpoint2i(-x,-y); }
		    public LBpoint2i inverse() { return new LBpoint2i(1/x,1/y); }
		    public LBpoint2f toFloat() { return new LBpoint2f(this); }
		    public LBpoint2i absolute() { x = x<0.f ? -x:x; y = y<0.f ? -y:y; return this; }
		    @Override public String toString() { return x+"|"+y; }
		    @Override public boolean equals(Object other) { return x ==((LBpoint2i)other).x && y ==((LBpoint2i)other).y; }
		    @Override protected Object clone() { return new LBpoint2i(this); }
		    
		    public LBpoint2i mirrorto(LBpoint2i o) { x = o.x*2-x; y = o.y*2-y; return this; }
		    public LBpoint2i mirrorto(double ox, double oy) { x = (int)ox*2-x; y = (int)oy*2-y; return this; }
		    public LBpoint2i mirroredto(LBpoint2i o) { return new LBpoint2i(o.x*2-x,o.y*2-y); }
		    public LBpoint2i mirroredto(double ox, double oy) { return new LBpoint2i(ox*2-x,oy*2-y); }
		    public double distanceto(LBpoint2i o) { int dx = x-o.x, dy = y-o.y; return Math.sqrt(dx*dx+dy*dy); }
		    public double distanceto(double ox, double oy) { int dx = (int)(x-ox), dy = (int)(y-oy); return Math.sqrt(dx*dx+dy*dy); }
		    
			public LBpoint2i copy(LBpoint2f o) { x=(int)o.x;y=(int)o.y; return this; }
			public LBpoint2i copy(LBpoint2i o) { x=o.x;y=o.y; return this; }
			public LBpoint2i copy(LBpoint2d o) { x=(int)o.x;y=(int)o.y; return this; }
		    public LBpoint2i copy(double ox, double oy) { x = (int)ox; y = (int)oy; return this; }
		    public LBpoint2i copy(double o) { x = (int)o; y = (int)o; return this; }
		    
		    public LBpoint2i increase(LBpoint2i o) { x+=o.x; y+=o.y; return this; }
		    public LBpoint2i sumwith(LBpoint2i o) { return new LBpoint2i(x+o.x,y+o.y); }
		    public LBpoint2i amplifie(LBpoint2i o) { x*=o.x; y*=o.y; return this; }
		    public LBpoint2i multiplie(LBpoint2i o) { return new LBpoint2i(x*o.x,y*o.y); }
		    public LBpoint2i increase(double ox, double oy) { x+=ox; y+=oy; return this; }
		    public LBpoint2i sumwith(double ox, double oy) { return new LBpoint2i(x+ox,y+oy); }
		    public LBpoint2i amplifie(double ox, double oy) { x*=ox; y*=oy; return this; }
		    public LBpoint2i multiplie(double ox, double oy) { return new LBpoint2i(x*ox,y*oy); }
		    public LBpoint2i increase(double o) { x+=o; y+=o; return this; }
		    public LBpoint2i sumwith(double o) { return new LBpoint2i(x+o,y+o); }
		    public LBpoint2i amplifie(double o) { x*=o; y*=o; return this; }
		    public LBpoint2i multiplie(double o) { return new LBpoint2i(x*o,y*o); }
		}
	
	
	
		/******************************************************************************************
		 * Simple Mouse Handling class for that for knowing lot's of things about mouse.
		 * Stores position of the mouse in "currentMousePosition" whener you call "lbUpdate()".
		 * If you set the value of "mouseOriginObject" to a component, "position"
		 * will be relative to that component (best practise is seting it to a JPanel).
		 * For using this object you can eighter add this as a "MouseListener" to a "JPanel"
		 * (not to a "Jframe") or you can call "onPress", "onRelease" on related functions
		 * but for handling "mouseInside" you need to set it on your own,
		 * but hopefully it is not changing any of other methods working style.
		 * Boolean "left", "right", "middle" indicates if they are down.
		 * Booleans that ends with "Readed" are setted to false only when
		 * the related key released automatically and there is not
		 * any other action, so YOU can handle button presses.
		 * The "wheel" starts at 0 and as wheel events happen it changes,
		 * so you can learn scroll of wheel relative to the one at start of aplication.
		 * Integers that end with "for" meant to tell you how many updates they stayed
		 * in the same state, for using this utility you must call lbUpdate() in every update.
		 ******************************************************************************************/
		public static class LBmouseHandle implements MouseListener, MouseWheelListener {
			public boolean left=false, right=false, midle=false, mouseInside=false;
			public boolean leftReaded=false, rightReaded=false, middleReaded=false;
			public LBpoint2i leftLastPressPosition=null, leftLastReleasePosition=null;
			public LBpoint2i rightLastPressPosition=null, rightLastReleasePosition=null;
			public int leftfor = 0, rightfor = 0, wheel = 0;
			public Component mouseOriginObject = null;
			public LBpoint2i position = new LBpoint2i();
			public void onPress(MouseEvent e) {
				switch(e.getButton()) {
				case MouseEvent.BUTTON2: midle = true; break;
				case MouseEvent.BUTTON1:
					left = true; leftfor = 0;
					leftLastPressPosition = new LBpoint2i(e.getX(), e.getY());
					break;
				case MouseEvent.BUTTON3:
					right = true; rightfor = 0;
					rightLastReleasePosition = new LBpoint2i(e.getX(), e.getY());
					break;
				}
			}
			public void onRelease(MouseEvent e) {
				switch(e.getButton()) {
				case MouseEvent.BUTTON2: midle = false; middleReaded = false; break;
				case MouseEvent.BUTTON1:
					left = false; leftfor = 0; leftReaded = false;
					leftLastReleasePosition = new LBpoint2i(e.getX(), e.getY());
					break;
				case MouseEvent.BUTTON3:
					right = false; rightfor = 0; rightReaded = false;
					rightLastReleasePosition = new LBpoint2i(e.getX(), e.getY());
					break;
				}
			}
			public void lbUpdate() {
				if(mouseOriginObject == null) { position = getPosition();
				} else { position = getPosition(mouseOriginObject); }
				leftfor++; rightfor++;
			}
			public LBpoint2i getPosition() {
				Point p = MouseInfo.getPointerInfo().getLocation();
				return new LBpoint2i(p.x, p.y);
			}
			public LBpoint2i getPosition(Component relative) {
				Point p = relative.getLocationOnScreen(); LBpoint2i r = getPosition();
				r.x -= p.x; r.y -= p.y;
				return r;
			}
			@Override public void mousePressed(MouseEvent e) { onPress(e); }
			@Override public void mouseReleased(MouseEvent e) { onRelease(e); }
			@Override public void mouseEntered(MouseEvent e) { mouseInside = true; }
			@Override public void mouseExited(MouseEvent e) { mouseInside = false; }
			@Override public void mouseClicked(MouseEvent e) { }
			@Override public void mouseWheelMoved(MouseWheelEvent e) { wheel += e.getWheelRotation(); }
		}

		/***************************************************************************************************************************
		 * Simple Keyboard handling class that tries to store the status of the keyboard.
		 * For using this you can eighter ad this as a keylistener or by calling "onKeyPress" and "onKeyRelease" buttons manually.
		 * For now it stores all the latin alphabet and numbers, also few control keys like: alt shift enter capslock control delete,
		 * altgraph, escape, backspace, tab. Also there is a copy of each key that ends with "Read", those are set to false when
		 * the associated key released, so you can do things like this:
		 * "if(enterRead) { return; } enterRead = true; YOUR CODE HERE"
		 ***************************************************************************************************************************/
		public static class LBkeyboardHandle implements KeyListener {
			public boolean a=false,b=false,c=false,d=false,e=false,f=false,g=false,h=false,i=false,
					j=false,k=false,l=false,m=false,n=false,o=false,p=false,r=false,s=false,t=false,
					u=false,v=false,y=false,z=false,q=false,w=false;
			public boolean up=false,down=false,left=false,right=false,control=false,alt=false, delete=false,
					altgraph=false,shift=false,enter=false,capslock=false,tab=false,comma=false,period=false,escape=false;
			public boolean quote=false,_0=false,_1=false,_2=false,_3=false,_4=false,_5=false,_6=false,_7=false,_8=false,_9=false;
			public boolean insert=false,np1=false,np2=false,np3=false,np4=false,np5=false,np6=false,np7=false,np8=false,np9=false;
			public boolean starN=false,minusN=false,backspace=false,numlock=false,nslash=false,star=false,substract=false;
			public boolean aRead=false,bRead=false,cRead=false,dRead=false,eRead=false,fRead=false,gRead=false,hRead=false,iRead=false,
					jRead=false,kRead=false,lRead=false,mRead=false,nRead=false,oRead=false,pRead=false,rRead=false,sRead=false,tRead=false,
					uRead=false,vRead=false,yRead=false,zRead=false,qRead=false,wRead=false;
			public boolean upRead=false,downRead=false,leftRead=false,rightRead=false,controlRead=false,
					altRead=false,deleteRead=false,altgraphRead=false,shiftRead=false,enterRead=false,
					capslockRead=false,tabRead=false,commaRead=false,periodRead=false,escapeRead=false;
			public boolean quoteRead=false,_0Read=false,_1Read=false,_2Read=false,_3Read=false,_4Read=false,
					_5Read=false,_6Read=false,_7Read=false,_8Read=false,_9Read=false;
			public boolean insertRead=false,np1Read=false,np2Read=false,np3Read=false,np4Read=false,
					np5Read=false,np6Read=false,np7Read=false,np8Read=false,np9Read=false;
			public boolean starNRead=false,minusNRead=false,backspaceRead=false,numlockRead=false,
					nslashRead=false,starRead=false,substractRead=false;
			public boolean ALT() { return alt || altgraph; } public boolean Star() { return star || starN; }
			public boolean ALTread() { return altRead || altgraphRead; } public boolean StarRead() { return starRead || starNRead; }
	        public void onKeyPress(int keyCode) { switch(keyCode) {
	        case KeyEvent.VK_COMMA:comma=true;break;case KeyEvent.VK_PERIOD:period=true;break;
	        case KeyEvent.VK_DELETE:delete=true;break;case KeyEvent.VK_BACK_SPACE:backspace=true;break;
	        case KeyEvent.VK_MINUS:minusN=true;break;case KeyEvent.VK_SUBTRACT:substract=true;break;
	        case KeyEvent.VK_ESCAPE:escape=true;break;case KeyEvent.VK_NUM_LOCK:numlock=true;break;
	        case KeyEvent.VK_INSERT:insert=true;break;case KeyEvent.VK_NUMPAD1:np1=true;break;
	        case KeyEvent.VK_NUMPAD2:np2=true;break;case KeyEvent.VK_NUMPAD3:np3=true;break;
	        case KeyEvent.VK_NUMPAD4:np4=true;break;case KeyEvent.VK_NUMPAD5:np5=true;break;
	        case KeyEvent.VK_NUMPAD6:np6=true;break;case KeyEvent.VK_NUMPAD7:np7=true;break;
	        case KeyEvent.VK_NUMPAD8:np8=true;break;case KeyEvent.VK_NUMPAD9:np9=true;break;
	        case KeyEvent.VK_A:a=true;break;case KeyEvent.VK_B:b=true;break;
	        case KeyEvent.VK_C:c=true;break;case KeyEvent.VK_D:d=true;break;
	        case KeyEvent.VK_E:e=true;break;case KeyEvent.VK_F:f=true;break;
	        case KeyEvent.VK_G:g=true;break;case KeyEvent.VK_H:h=true;break;
	        case KeyEvent.VK_I:i=true;break;case KeyEvent.VK_J:j=true;break;
	        case KeyEvent.VK_K:k=true;break;case KeyEvent.VK_L:l=true;break;
	        case KeyEvent.VK_M:m=true;break;case KeyEvent.VK_N:n=true;break;
	        case KeyEvent.VK_O:o=true;break;case KeyEvent.VK_P:p=true;break;
	        case KeyEvent.VK_R:r=true;break;case KeyEvent.VK_S:s=true;break;
	        case KeyEvent.VK_T:t=true;break;case KeyEvent.VK_U:u=true;break;
	        case KeyEvent.VK_V:v=true;break;case KeyEvent.VK_Y:y=true;break;
	        case KeyEvent.VK_Z:z=true;break;case KeyEvent.VK_Q:q=true;break;
	        case KeyEvent.VK_W:w=true;break;case KeyEvent.VK_UP:up=true;break;
	        case KeyEvent.VK_DOWN:down=true;break;case KeyEvent.VK_LEFT:left=true;break;
	        case KeyEvent.VK_RIGHT:right=true;break;case KeyEvent.VK_CONTROL:control=true;break;
	        case KeyEvent.VK_ALT:alt=true;break;case KeyEvent.VK_ALT_GRAPH:altgraph=true;break;
	        case KeyEvent.VK_SHIFT:shift=true;break;case KeyEvent.VK_ENTER:enter=true;break;
	        case KeyEvent.VK_CAPS_LOCK:capslock=true;break;case KeyEvent.VK_TAB:tab=true;break;
	        case KeyEvent.VK_QUOTEDBL:quote=true;break;
	        case KeyEvent.VK_1:_1=true;break;case KeyEvent.VK_2:_2=true;break;
	        case KeyEvent.VK_3:_3=true;break;case KeyEvent.VK_4:_4=true;break;
	        case KeyEvent.VK_5:_5=true;break;case KeyEvent.VK_6:_6=true;break;
	        case KeyEvent.VK_7:_7=true;break;case KeyEvent.VK_8:_8=true;break;
	        case KeyEvent.VK_9:_9=true;break;case KeyEvent.VK_0:_0=true;break;
	        } }
	        public void onKeyRelease(int keyCode) { switch(keyCode) {
	        case KeyEvent.VK_COMMA:comma=false;commaRead=false;break;case KeyEvent.VK_PERIOD:period=false;periodRead=false;break;
	        case KeyEvent.VK_DELETE:delete=false;deleteRead=false;break;
	        case KeyEvent.VK_BACK_SPACE:backspace=false;backspaceRead=false;break;
	        case KeyEvent.VK_MINUS:minusN=false;minusNRead=false;break;case KeyEvent.VK_SUBTRACT:substract=false;substractRead=false;break;
	        case KeyEvent.VK_ESCAPE:escape=false;escapeRead=false;break;case KeyEvent.VK_NUM_LOCK:numlock=false;numlockRead=false;break;
	        case KeyEvent.VK_INSERT:insert=false;insertRead=false;break;case KeyEvent.VK_NUMPAD1:np1=false;np1Read=false;break;
	        case KeyEvent.VK_NUMPAD2:np2=false;np2Read=false;break;case KeyEvent.VK_NUMPAD3:np3=false;np3Read=false;break;
	        case KeyEvent.VK_NUMPAD4:np4=false;np4Read=false;break;case KeyEvent.VK_NUMPAD5:np5=false;np5Read=false;break;
	        case KeyEvent.VK_NUMPAD6:np6=false;np6Read=false;break;case KeyEvent.VK_NUMPAD7:np7=false;np7Read=false;break;
	        case KeyEvent.VK_NUMPAD8:np8=false;np8Read=false;break;case KeyEvent.VK_NUMPAD9:np9=false;np9Read=false;break;
	        case KeyEvent.VK_A:a=false;aRead=false;break;case KeyEvent.VK_B:b=false;bRead=false;break;
	        case KeyEvent.VK_C:c=false;cRead=false;break;case KeyEvent.VK_D:d=false;dRead=false;break;
	        case KeyEvent.VK_E:e=false;eRead=false;break;case KeyEvent.VK_F:f=false;fRead=false;break;
	        case KeyEvent.VK_G:g=false;gRead=false;break;case KeyEvent.VK_H:h=false;hRead=false;break;
	        case KeyEvent.VK_I:i=false;iRead=false;break;case KeyEvent.VK_J:j=false;jRead=false;break;
	        case KeyEvent.VK_K:k=false;kRead=false;break;case KeyEvent.VK_L:l=false;lRead=false;break;
	        case KeyEvent.VK_M:m=false;mRead=false;break;case KeyEvent.VK_N:n=false;nRead=false;break;
	        case KeyEvent.VK_O:o=false;oRead=false;break;case KeyEvent.VK_P:p=false;pRead=false;break;
	        case KeyEvent.VK_R:r=false;rRead=false;break;case KeyEvent.VK_S:s=false;sRead=false;break;
	        case KeyEvent.VK_T:t=false;tRead=false;break;case KeyEvent.VK_U:u=false;uRead=false;break;
	        case KeyEvent.VK_V:v=false;vRead=false;break;case KeyEvent.VK_Y:y=false;yRead=false;break;
	        case KeyEvent.VK_Z:z=false;zRead=false;break;case KeyEvent.VK_Q:q=false;qRead=false;break;
	        case KeyEvent.VK_W:w=false;wRead=false;break;case KeyEvent.VK_UP:up=false;upRead=false;break;
	        case KeyEvent.VK_DOWN:down=false;downRead=false;break;case KeyEvent.VK_LEFT:left=false;leftRead=false;break;
	        case KeyEvent.VK_RIGHT:right=false;rightRead=false;break;case KeyEvent.VK_CONTROL:control=false;controlRead=false;break;
	        case KeyEvent.VK_ALT:alt=false;altRead=false;break;case KeyEvent.VK_ALT_GRAPH:altgraph=false;altgraphRead=false;break;
	        case KeyEvent.VK_SHIFT:shift=false;shiftRead=false;break;case KeyEvent.VK_ENTER:enter=false;enterRead=false;break;
	        case KeyEvent.VK_CAPS_LOCK:capslock=false;capslockRead=false;break;case KeyEvent.VK_TAB:tab=false;tabRead=false;break;
	        case KeyEvent.VK_QUOTEDBL:quote=false;quoteRead=false;break;
	        case KeyEvent.VK_1:_1=false;_1Read=false;break;case KeyEvent.VK_2:_2=false;_2Read=false;break;
	        case KeyEvent.VK_3:_3=false;_3Read=false;break;case KeyEvent.VK_4:_4=false;_4Read=false;break;
	        case KeyEvent.VK_5:_5=false;_5Read=false;break;case KeyEvent.VK_6:_6=false;_6Read=false;break;
	        case KeyEvent.VK_7:_7=false;_7Read=false;break;case KeyEvent.VK_8:_8=false;_8Read=false;break;
	        case KeyEvent.VK_9:_9=false;_9Read=false;break;case KeyEvent.VK_0:_0=false;_0Read=false;break;
	        } }
			@Override public void keyTyped(KeyEvent e) { }
			@Override public void keyPressed(KeyEvent e) { onKeyPress(e.getExtendedKeyCode()); }
			@Override public void keyReleased(KeyEvent e) { onKeyRelease(e.getExtendedKeyCode()); }
		}
	
}