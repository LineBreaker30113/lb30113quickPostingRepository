package uinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MYleftMostFolderFileLikeGUI1 extends JPanel {
	private static final long serialVersionUID = 4188045391673045514L;

	@SuppressWarnings("unused")
	private static void log(String message) { System.out.println(message); }


	public static class LBtextSizeHandler {
		public Font font;
		public AffineTransform affineTransform;
		public FontRenderContext fontRenderContext;
		public LineMetrics fontLineMetrics;
		
		public LBtextSizeHandler() { initialize(new Font(Font.SANS_SERIF, Font.PLAIN, 14)); }
		public LBtextSizeHandler(Font font) { initialize(font); }
		
		public void initialize(Font font) {
			this.font = font;
			affineTransform = new AffineTransform();
			fontRenderContext = new FontRenderContext(affineTransform,true,true);
			fontLineMetrics = font.getLineMetrics("", fontRenderContext);
		}

		public LineMetrics getFontLineMetrics(char[] chars) {
			return font.getLineMetrics(chars, 0, chars.length, fontRenderContext);
		}
		
		public int getTextWidth(char[][] text) {
			int result = 0;
			for(int i=0;i!=text.length;i++) {
				int textWidth = (int)(font.getStringBounds(new String(text[i]), fontRenderContext).getWidth());
				result = result > textWidth ? result : textWidth;
			}
			return result;
		}
		public int getTextWidth(char[] text) {
			return (int)(font.getStringBounds(new String(text), fontRenderContext).getWidth());
		}
		public int getTextHeight(char[][] text) {
			double result =  fontLineMetrics.getDescent() + fontLineMetrics.getLeading();
			for(int i=0;i!=text.length;i++) {
				result += getFontLineMetrics(text[i]).getAscent();
			}
			return (int) result;
		}
		public int getTextHeight() {
			return (int) (fontLineMetrics.getAscent() + fontLineMetrics.getDescent());
		}
		public char[][] translateTOlines(char[] text, int width) {
			ArrayList<char[]> result = new ArrayList<char[]>();
			ArrayList<Integer> spaces = new ArrayList<Integer>();
			spaces.add(Integer.valueOf(0));
			int lastSpaceIndex = 0, lineIndex = 0;
			for(int i = 0; i != text.length; i++) {
				boolean outOFbound = true;
				while(font.getStringBounds(text, spaces.get(lineIndex), i, fontRenderContext).getWidth() < width) {
					if(i == text.length -1) { lastSpaceIndex = i+1; outOFbound = false; break; }
					if(text[i] == '\n') { lastSpaceIndex = i; outOFbound = false; break; }
					if(text[i] == ' ' || text[i] == '\t') { lastSpaceIndex = i; }
					i++;
				}
				if(spaces.get(lineIndex) != lastSpaceIndex) {
					result.add(getSection(text, spaces.get(lineIndex), lastSpaceIndex));
				} else {
					result.add(getSection(text, spaces.get(lineIndex), i)); lastSpaceIndex = i-1;
				}
				spaces.add(Integer.valueOf(++lastSpaceIndex));
				if(outOFbound && i == text.length - 1) { result.add(getSection(text, lastSpaceIndex, text.length)); }
				lineIndex++;
			}
			char[][] output = new char[result.size()][];
			for(int i = 0; i != output.length; i++) { output[i] = result.get(i); }
			return output;
		}
		public char[][] translateTOparagraphs(char[] text, int width) {
			ArrayList<char[]> result = new ArrayList<char[]>();
			ArrayList<Integer> spaces = new ArrayList<Integer>();
			spaces.add(Integer.valueOf(0));
			int lastSpaceIndex = 0, lineIndex = 0;
			for(int i = 1; i != text.length; i++) {
				if(text[i-1] == '\n') { result.add(new char[0]); }
				boolean outOFbound = true;
				while(font.getStringBounds(text, spaces.get(lineIndex), i, fontRenderContext).getWidth() < width) {
					if(i == text.length -1) { lastSpaceIndex = i+1; outOFbound = false; break; }
					if(text[i] == '\n') { lastSpaceIndex = i; outOFbound = false; break; }
					if(text[i] == ' ' || text[i] == '\t') { lastSpaceIndex = i; }
					i++;
				}
				if(spaces.get(lineIndex) != lastSpaceIndex) {
					result.add(getSection(text, spaces.get(lineIndex), lastSpaceIndex));
				} else {
					result.add(getSection(text, spaces.get(lineIndex), i)); lastSpaceIndex = i-1;
				}
				spaces.add(Integer.valueOf(++lastSpaceIndex));
				if(outOFbound && i == text.length - 1) { result.add(getSection(text, lastSpaceIndex, text.length)); }
				lineIndex++; 
			}
			char[][] output = new char[result.size()][];
			for(int i = 0; i != output.length; i++) { output[i] = result.get(i); }
			return output;
		}
		private char[] getSection(char[] text, int start, int end) {
			char[] result = new char[end - start];
			for(int i = start; i != end; i++) { result[i - start] = text[i]; }
			return result;
		}

	}

	public static interface LB_LMFF_GUIstyleI {
		public Color TextColor = Color.RED, BGcolor = Color.GREEN;
		public Color BorderColor = Color.BLUE, TitleBGcolor = Color.CYAN;
		public Color TitleColor = Color.RED;
		public LBtextSizeHandler TSH = new LBtextSizeHandler();
		public int SpacingX = 10, SpacingY = 10, BorderW = 10, BorderH = 10;

		public LB_LMFF_GUIstyleI getParent(); public void setParent(LB_LMFF_GUIstyleI nparent);
		public void setOwner(LB_LMFF_GUIsection owner); public LB_LMFF_GUIsection getOwner();
		public LB_LMFF_GUIstyleI createChild();

		public Color getTextColor(); public void setTextColor(Color color);
		public Color getBGcolor(); public void setBGcolor(Color color);
		public Color getBorderColor(); public void setBorderColor(Color color);
		public Color getTitleBGcolor(); public void setTitleBGcolor(Color color);
		public Color getTitleColor(); public void setTitleColor(Color color);

		public LBtextSizeHandler getTSH(); public void setTSH(LBtextSizeHandler ntsh);

		public int getSpacingX(); public void setSpacingX(int value);
		public int getSpacingY(); public void setSpacingY(int value);
		public int getBorderW(); public void setBorderW(int value);
		public int getBorderH(); public void setBorderH(int value);

	}
	
	public static class LB_LMFF_GUIstyle implements LB_LMFF_GUIstyleI {
		public LB_LMFF_GUIstyleI parentStyle = null;
		public LB_LMFF_GUIsection ownerElement = null;
		public LBtextSizeHandler tsh = null;
		public Color textColor = null, bgColor = null, borderColor = null, titleBGcolor = null, titleColor = null;
		public int spacingX = -1, spacingY = -1, borderW = -1, borderH = -1;

		@Override public LB_LMFF_GUIstyleI getParent() { return parentStyle; }
		@Override
		public void setParent(LB_LMFF_GUIstyleI nparent) {
			parentStyle = nparent; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override public LB_LMFF_GUIsection getOwner() { return ownerElement; }
		@Override
		public void setOwner(LB_LMFF_GUIsection owner) {
			ownerElement = owner; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override
		public LB_LMFF_GUIstyleI createChild() {
			LB_LMFF_GUIstyleI result = new LB_LMFF_GUIstyle();
			result.setParent(this);
			return result;
		}

		@Override
		public Color getTextColor() {
			if(textColor != null) { return textColor; }
			if(parentStyle != null) { return parentStyle.getTextColor(); }
			return TextColor;
		}
		@Override
		public Color getBGcolor() {
			if(bgColor != null) { return bgColor; }
			if(parentStyle != null) { return parentStyle.getBGcolor(); }
			return BGcolor;
		}
		@Override
		public Color getBorderColor() {
			if(borderColor != null) { return borderColor; }
			if(parentStyle != null) { return parentStyle.getBorderColor(); }
			return BorderColor;
		}
		@Override
		public Color getTitleBGcolor() {
			if(titleBGcolor != null) { return titleBGcolor; }
			if(parentStyle != null) { return parentStyle.getTitleBGcolor(); }
			return TitleBGcolor;
		}
		@Override
		public Color getTitleColor() {
			if(titleColor != null) { return titleColor; }
			if(parentStyle != null) { return parentStyle.getTitleColor(); }
			return TitleColor;
		}

		@Override
		public LBtextSizeHandler getTSH() {
			if(tsh != null) { return tsh; }
			if(parentStyle != null) { return parentStyle.getTSH(); }
			return TSH;
		}

		@Override
		public int getSpacingX() {
			if(spacingX != -1) { return spacingX; }
			if(parentStyle != null) { return parentStyle.getSpacingX(); }
			return SpacingX;
		}
		@Override
		public int getSpacingY() {
			if(spacingY != -1) { return spacingY; }
			if(parentStyle != null) { return parentStyle.getSpacingY(); }
			return SpacingY;
		}
		@Override
		public int getBorderW() {
			if(borderW != -1) { return borderW; }
			if(parentStyle != null) { return parentStyle.getBorderW(); }
			return BorderW;
		}
		@Override
		public int getBorderH() {
			if(borderH != -1) { return borderH; }
			if(parentStyle != null) { return parentStyle.getBorderH(); }
			return BorderH;
		}

		@Override
		public void setTextColor(Color color) {
			textColor = color; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override
		public void setBGcolor(Color color) {
			bgColor = color; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override
		public void setBorderColor(Color color) {
			borderColor = color; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override
		public void setTitleBGcolor(Color color) {
			titleBGcolor = color; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override
		public void setTitleColor(Color color) {
			titleColor = color; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override
		public void setTSH(LBtextSizeHandler ntsh) {
			tsh = ntsh; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override
		public void setSpacingX(int value) {
			spacingX = value; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override
		public void setSpacingY(int value) {
			spacingY = value; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override
		public void setBorderW(int value) {
			borderW = value; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}
		@Override
		public void setBorderH(int value) {
			borderH = value; if(ownerElement!=null) { ownerElement.recastNeeded = true; }
		}

	}

	public static abstract class LB_LMFF_GUIsection implements MouseListener {
		public LB_LMFF_GUIsection father;
		public int coloredW, coloredH, x, y;
		public boolean recastNeeded = true, mouseHolding = false;
		public LB_LMFF_GUIstyleI design;

		public LB_LMFF_GUIsection(LB_LMFF_GUIsection father) {
			this.father = father;
			if(father == null) {
				this.design = new LB_LMFF_GUIstyle();
			} else {
				this.design = father.design.createChild();
			}
			this.design.setOwner(this);
		}
		
		@Override public void mousePressed(MouseEvent event) { lbONmousePressed(event); }
		@Override public void mouseReleased(MouseEvent event) { lbONmouseRelease(event); }
		@Override public void mouseClicked(MouseEvent event) { lbONmouseClick(event); }
		@Override public void mouseEntered(MouseEvent event) { }
		@Override public void mouseExited(MouseEvent event) { }

		public abstract void lbONmousePressed(MouseEvent event);
		public abstract void lbONmouseRelease(MouseEvent event);
		public abstract void lbONmouseClick(MouseEvent event);

		public abstract void lbUpdate();
		public abstract void lbPaint(Graphics2D target);

	}

	public static class LB_LMFF_GUIfolder extends LB_LMFF_GUIsection {
		public char[][] title; public char[] origin; public int titleH;
		public boolean expanded = false, expanderHolding = false;
		public ArrayList<LB_LMFF_GUIsection> childs = new ArrayList<LB_LMFF_GUIsection>();
		public Polygon condenseArrow, expandArrow;

		public LB_LMFF_GUIfolder(LB_LMFF_GUIsection father) {
			super(father);
			condenseArrow = new Polygon(); expandArrow = new Polygon();
			condenseArrow.addPoint(0, design.getTSH().getTextHeight());
			condenseArrow.addPoint(design.getTSH().getTextHeight(), design.getTSH().getTextHeight());
			condenseArrow.addPoint(design.getTSH().getTextHeight()/2, 0);
			expandArrow.addPoint(0, 0);
			expandArrow.addPoint(design.getTSH().getTextHeight(), 0);
			expandArrow.addPoint(design.getTSH().getTextHeight()/2, design.getTSH().getTextHeight());
		}

		@Override
		public void lbONmousePressed(MouseEvent event) {
			int mx = event.getX() - x - design.getBorderW(), my = event.getY() - y - design.getBorderH();
			if(mx < 0 || my < 0 || mx > coloredW - 2 * design.getBorderW() || my > coloredH) { return; }
			if(recastNeeded) { lbUpdate(); }
			if(mouseHolding) { return; } mouseHolding = true;
			if(my < titleH + design.getBorderH()) { expanderHolding = true; return; }
			for(LB_LMFF_GUIsection child : childs) { child.lbONmousePressed(event); }
		}
		@Override
		public void lbONmouseRelease(MouseEvent event) {
			int mx = event.getX() - x - design.getBorderW(), my = event.getY() - y - design.getBorderH();
			if(expanderHolding) {
				mouseHolding = false; expanderHolding = false;
				if(mx < 0 || my < 0 || mx > coloredW - 2 * design.getBorderW() || my > titleH)
				{ return; } else { expanded = !expanded; }
			} else if(mouseHolding) {
				for(LB_LMFF_GUIsection child : childs) { child.lbONmouseRelease(event); }
			}
			mouseHolding = false; return;
		}
		@Override public void lbONmouseClick(MouseEvent event) { }
		
		@Override
		public void lbUpdate() {
			if(father != null) {
				father.recastNeeded = true;
				coloredW = father.coloredW - 2 * father.design.getSpacingX();
			}
			int titleW = coloredW - 2 * design.getBorderW() - 4 * design.getSpacingX();
			titleW -= design.getTSH().getTextHeight() + design.getSpacingX();
			title = design.getTSH().translateTOlines(origin, titleW);
			titleH = design.getTSH().getTextHeight(title) + 2 * design.getSpacingY();
			coloredH = titleH + 2 * design.getBorderH();
			condenseArrow = new Polygon(); expandArrow = new Polygon();
			condenseArrow.addPoint(0, design.getTSH().getTextHeight());
			condenseArrow.addPoint(design.getTSH().getTextHeight(), design.getTSH().getTextHeight());
			condenseArrow.addPoint(design.getTSH().getTextHeight()/2, 0);
			expandArrow.addPoint(0, 0);
			expandArrow.addPoint(design.getTSH().getTextHeight(), 0);
			expandArrow.addPoint(design.getTSH().getTextHeight()/2, design.getTSH().getTextHeight());
			if(!expanded) { recastNeeded = false; return; }
			coloredH += design.getSpacingY();
			for(LB_LMFF_GUIsection file : childs) {
				file.lbUpdate(); file.y = y + coloredH;
				file.x = x + design.getBorderW() + design.getSpacingX();
				coloredH += file.coloredH + design.getSpacingY();
			}
			coloredH += design.getBorderH();
			recastNeeded = false; return;
		}
		@Override
		public void lbPaint(Graphics2D target) {
			if(recastNeeded) { lbUpdate(); }
			int rectW = coloredW, rectH = coloredH, currentX = x, currentY = y;
			target.setColor(design.getBorderColor());
			target.fillRect(currentX, currentY, rectW, rectH);
			// border has drawn
			currentX += design.getBorderW(); currentY += design.getBorderH(); rectW -= design.getBorderW()*2;
			target.setColor(design.getTitleBGcolor());
			target.fillRect(currentX, currentY, rectW, titleH);
			// title has filled
			currentX += design.getSpacingX(); currentY += design.getSpacingY();
			target.setColor(design.getTextColor());
			Polygon triangle = expanded ? condenseArrow : expandArrow;
			for(int i = 0; i != 3; i++) {
				triangle.xpoints[i] += currentX; triangle.ypoints[i] += currentY;
			}
			target.fill(triangle);
			for(int i = 0; i != 3; i++) {
				triangle.xpoints[i] -= currentX; triangle.ypoints[i] -= currentY;
			}
			currentX += design.getSpacingX() + design.getTSH().getTextHeight();
			target.setFont(design.getTSH().font);
			double lineHeight = (design.getTSH().getTextHeight(title)) / title.length;
			double cy = currentY + lineHeight
					- design.getTSH().fontLineMetrics.getDescent() - design.getTSH().fontLineMetrics.getLeading();
			for(int i = 0; i != title.length; i++) {
				target.drawChars(title[i], 0, title[i].length, currentX, (int) (cy + lineHeight*i));
			}
			if(!expanded) { return; }
			// title has written
			currentX -= design.getSpacingX() + design.getTSH().getTextHeight();
			currentX -= design.getSpacingX(); currentY += titleH - design.getSpacingY() + design.getBorderH();
			rectH -= 3 * design.getBorderH() + titleH;
			target.setColor(design.getBGcolor());
			target.fillRect(currentX, currentY, rectW, rectH);
			// bakground has filled
			for(LB_LMFF_GUIsection file : childs) { file.lbPaint(target); }
		}

	}
	
	public static class LB_LMFF_GUIfile extends LB_LMFF_GUIsection {
		public char[][] text; public char[] origin; public boolean paragraphs = false;

		@Override public void lbONmousePressed(MouseEvent event) { }
		@Override public void lbONmouseRelease(MouseEvent event) { }
		@Override public void lbONmouseClick(MouseEvent event) { }
		
		public void checkStatus() {
			
		}

		public void reCalculate() {
			if(father != null) {
				father.recastNeeded = true;
				coloredW = father.coloredW - 2 * (father.design.getSpacingX() + father.design.getBorderW());
			}
			if(paragraphs) {
				text = design.getTSH().translateTOparagraphs(origin, coloredW - 2 * design.getBorderW() - 4 * design.getSpacingX());
			} else {
				text = design.getTSH().translateTOlines(origin, coloredW - 2 * design.getBorderW() - 4 * design.getSpacingX());
			}
			coloredH = design.getTSH().getTextHeight(text) + 2 * (design.getBorderH() + design.getSpacingY());
			recastNeeded = false; return;
		}
		
		@Override
		public void lbUpdate() {
			if(father != null) {
				father.recastNeeded = true;
				coloredW = father.coloredW - 2 * (father.design.getSpacingX() + father.design.getBorderW());
			}
			if(paragraphs) {
				text = design.getTSH().translateTOparagraphs(origin, coloredW - 2 * design.getBorderW() - 4 * design.getSpacingX());
			} else {
				text = design.getTSH().translateTOlines(origin, coloredW - 2 * design.getBorderW() - 4 * design.getSpacingX());
			}
			coloredH = design.getTSH().getTextHeight(text) + 2 * (design.getBorderH() + design.getSpacingY());
			recastNeeded = false; return;
		}
		@Override
		public void lbPaint(Graphics2D target) {
			int rectW = coloredW, rectH = coloredH, currentX = x, currentY = y;
			target.setColor(design.getBorderColor());
			target.fillRect(currentX, currentY, rectW, rectH);
			// border has drawn
			currentX += design.getBorderW(); currentY += design.getBorderH();
			rectW -= design.getBorderW()*2; rectH -= design.getBorderH()*2;
			target.setColor(design.getBGcolor());
			target.fillRect(currentX, currentY, rectW, rectH);
			// bakground has filled
			currentX += design.getSpacingX(); currentY += design.getSpacingY();
			target.setColor(design.getTextColor());
			target.setFont(design.getTSH().font);
			double lineHeight = (design.getTSH().getTextHeight(text) + design.getTSH().fontLineMetrics.getLeading()) / text.length;
			double cy = currentY + lineHeight - design.getTSH().fontLineMetrics.getDescent()
					- design.getTSH().fontLineMetrics.getLeading();
			for(int i=0;i!=text.length;i++) {
				target.drawChars(text[i], 0, text[i].length, currentX, (int) (cy + lineHeight*i));
			}
		}
		
		public LB_LMFF_GUIfile(LB_LMFF_GUIsection father) {
			super(father);
		}

	}

	public LBtextSizeHandler tsh = new LBtextSizeHandler(new Font(Font.SANS_SERIF, Font.BOLD, 34));
	public LB_LMFF_GUIstyleI design = new LB_LMFF_GUIstyle();
	public LB_LMFF_GUIfile guif;
	public LB_LMFF_GUIfolder guiF = new LB_LMFF_GUIfolder(null);
	
	public static void main(String[] args) {
		Tester test = new Tester();
		test.mengine.guiF.origin = "TITLE!".toCharArray();
		test.mengine.LBintialize();
		test.mengine.design.setTSH(test.mengine.tsh);
		test.mengine.guif = new LB_LMFF_GUIfile(test.mengine.guiF);
		test.mengine.guiF.design = test.mengine.design;
		test.mengine.guiF.childs.add(test.mengine.guif);
		test.mengine.guiF.expanded = false;
		test.mengine.guiF.x = 10; test.mengine.guiF.y = 10;
		test.mengine.guif.design = test.mengine.design;
		test.mengine.guif.origin =
				("dublevea denitsevi gurug temistene lakevitsum dolores diemto morophane "
				+" tesportulyou merpahçin domeris nulke nansiya moloris sithem probin delecustom"
				+" dentorniesto morulka diethem is telefun do setmonte turbensat monortum ertiam"
				+" tesportulyou merpahçin domeris nulke nansiya moloris sithem probin delecustom"
				+" dentorniesto morulka diethem is telefun do setmonte turbensat monortum ertiam"
				+" dentrun isterd urovanda kartiyem helecust amosoc latis rephonic aqorsis diest."
				).toCharArray();
		test.mengine.guif.father = test.mengine.guiF;
		test.mengine.addMouseListener(test.mengine.guiF);
	}
	public void lbPaint(Graphics2D target) {
		guiF.coloredW = getWidth() - 20;
		guiF.lbUpdate();
		guiF.lbPaint(target);
	}

	public Timer utimer = new Timer(), dtimer = new Timer();
	public TimerTask utask = new TimerTask() { @Override public void run() { aplication.lbUpdate(); } };
	public TimerTask dtask = new TimerTask() { @Override public void run() { aplication.repaint(); } };

	public int fdelay = 33;
	public double frate = 1000.f / fdelay, inverseFrate = fdelay / 1000.f;
	
	public Aplication aplication;

	public void LBintialize() {
		frate = 1000.f; frate /= fdelay;
		inverseFrate = fdelay; inverseFrate /= 1000.f;

		dtimer.scheduleAtFixedRate(dtask, 0, fdelay);
		utimer.scheduleAtFixedRate(utask, 0, fdelay);

	}
	public void lbUpdate() {
	}

	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D target = (Graphics2D) graphics;
		target.setBackground(Color.BLACK);
		target.clearRect(0, 0, getWidth(), getHeight());
		aplication.lbPaint(target);
	}
	public static abstract class Aplication extends JFrame {
		private static final long serialVersionUID = 3703915023855376195L;
		public MYleftMostFolderFileLikeGUI1 mengine;
		
		public Aplication() {
			super.setDefaultCloseOperation(EXIT_ON_CLOSE);
			mengine = new MYleftMostFolderFileLikeGUI1(this);
			super.pack();
			super.setLocationRelativeTo(null);
			super.setVisible(true);
		}

		public abstract void lbUpdate();
		public abstract void lbPaint(Graphics2D target);
	}
	public MYleftMostFolderFileLikeGUI1(Aplication aplication) {
		this.aplication = aplication;
		super.setPreferredSize(new Dimension(1050, 700));
		super.setPreferredSize(new Dimension(800, 600));
		super.setDoubleBuffered(true);
		aplication.add(this);
	}
	private static class Tester extends Aplication {
		private static final long serialVersionUID = -497904516868404005L;
		@Override public void lbUpdate() { mengine.lbUpdate(); }
		@Override public void lbPaint(Graphics2D target) { mengine.lbPaint(target); }
	}
	
}