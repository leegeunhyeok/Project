import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Main extends JFrame {
	private final int WIDTH = 500;
	private final int HEIGHT = 600;

	private boolean DbufferOn = false;
	private boolean move_down = true;

	private final int x = 100;
	private final int y_MAX = 200;
	private final int y_MIN = 50;
	private int y1, y2;

	private Image iconImage = new ImageIcon(Main.class.getResource("images/test.png")).getImage();
	private Image img;
	private Graphics img_g;

	private ImageIcon button_normal = new ImageIcon(Main.class.getResource("images/button_start.png")); //리소스 이미지 등록
	private ImageIcon button_entered = new ImageIcon(Main.class.getResource("images/button_start_entered.png"));

	public Main() {
		setTitle("Double Buffering");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLayout(null);
		
		y1 = y_MIN;
		y2 = y_MAX;

		JButton btn = new JButton(button_normal); //button_normal이미지의 버튼생성
		btn.setBounds(100, 300, 300, 100); //x, y 지점에서 너비 300, 높이 100 버튼
		btn.setBorderPainted(false); //테두리 X
		btn.setContentAreaFilled(false); //이미지파일 투명한 영역 색채우기 비활성
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				//마우스가 버튼위에 올라갔을 때 (진입)
				
				btn.setIcon(button_entered);
				btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//마우스가 버튼 밖으로 나갈 때
				
				btn.setIcon(button_normal);
				btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				DbufferOn = !DbufferOn;
			}
		});
		add(btn); //버튼을 컨테이너위에 배치
	}

	public void paint(Graphics g) {
		//더블버퍼링 Off
		if (DbufferOn == false) {
			paintComponents(g);
			g.drawLine(x, y1, x + 300, y2);
			g.drawImage(iconImage, 400, 500, null);
			g.drawString("Double Buffer : Off", 100, 500);
		} else { 
			//더블버퍼링 On
			//-아래 과정은 화면에 바로 그리지 않고 메모리 내에서 이루어짐-//
			img = createImage(WIDTH, HEIGHT);
			img_g = img.getGraphics();
			paintComponents(img_g);
			img_g.drawLine(x, y1, x + 300, y2);
			img_g.drawImage(iconImage, 400, 500, null);
			img_g.drawString("Double Buffer : On ", 100, 500);
			//----------------------------------------------------//
			g.drawImage(img, 0, 0, null); //위의 과정을 마친 이미지를 한번만 화면에 그림
		}
		
		YposChange(); //직선의 좌표를 변경하는 메소드
		repaint(); //강제로 paint함수 호출 
	}

	public void YposChange() {
		if (move_down) {
			y1++;
			y2--;
			if (y1 >= y_MAX) {
				move_down = !move_down;
			}
		}

		if (!move_down) {
			y1--;
			y2++;
			if (y1 <= y_MIN) {
				move_down = !move_down;
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}