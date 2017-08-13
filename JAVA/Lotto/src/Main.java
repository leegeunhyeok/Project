import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main extends JFrame implements Runnable{
	final String base_url = "http://www.nlotto.co.kr/gameResult.do?method=byWin&drwNo=";
	JLabel label_1, label_2, label_3, label_4, label_5, label_6, label_7, label_b;
	String[] lotto_num = new String[8]; //로또 번호 저장배열
	String ep_num; //회차 번호
	JPanel mov_btn_layout; //상단 버튼을 배치할 패널
	JPanel number_layout; //숫자 이미지를 배치할 패널
	JPanel input_layout; //하단에 텍스트필드, 버튼을 배치할 패널
	JButton prev_btn = new JButton("이전");
	JButton next_btn = new JButton("다음");
	JButton refresh_btn = new JButton("해당 회차로");
	JLabel print_ep; //회차번호를 보여줄 라벨
	ImageIcon img_1, img_2, img_3, img_4, img_5, img_6, img_7;
	int Max_ep = 0;
	
	//생성자
	public Main(){
		Init();
		refresh("");
	}
	
	//Gui 초기설정
	public void Init(){
		super.setTitle("Lotto Number");
		label_1 = new JLabel();
		label_2 = new JLabel();
		label_3 = new JLabel();
		label_4 = new JLabel();
		label_5 = new JLabel();
		label_6 = new JLabel();
		label_7 = new JLabel();
		label_b = new JLabel("+");
		
		number_layout = new JPanel();
		number_layout.setLayout(new FlowLayout());
		
		mov_btn_layout = new JPanel();
		mov_btn_layout.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
		
		input_layout = new JPanel();
		input_layout.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		print_ep = new JLabel("0");
		
		//텍스트 입력칸의 크기: 최대 5글자까지 받음
		JTextField text = new JTextField(5);
		
		
		//해당 버튼에 리스너 작성
		prev_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = StringToInt(lotto_num[7]);
				if(i-1 > 0){
					i-=1;
					lotto_num[7] = Integer.toString(i);
					refresh(lotto_num[7]); //이전 회차로 새로고침
				}
			}
		});
		
		next_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = StringToInt(lotto_num[7]);
				if(i+1 <= Max_ep){
					i+=1;
					lotto_num[7] = Integer.toString(i);
					refresh(lotto_num[7]); //다음 회차로 새로고침
				}
			}
		});

		refresh_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = StringToInt(text.getText());
				
				//사용자가 입력한 회차가 MAX 회차보다 크지 않을 경우 데이터 불러옴.
				if(n <= Max_ep){
					lotto_num[7] = getNumberOnly(text.getText());
					text.setText(null);
					refresh(getNumberOnly(lotto_num[7]));
				}
			}
		});
		
		
		//패널에 라벨 배치
		number_layout.add(label_1);
		number_layout.add(label_2);
		number_layout.add(label_3);
		number_layout.add(label_4);
		number_layout.add(label_5);
		number_layout.add(label_6);
		number_layout.add(label_b);
		number_layout.add(label_7);
        //패널에 버튼 배치
		mov_btn_layout.add(prev_btn);
		mov_btn_layout.add(print_ep);
		mov_btn_layout.add(next_btn);
		
		
		input_layout.add(text);
		input_layout.add(refresh_btn);
		
		//위의 패널들을 컨테이너에 배치
		add("North", mov_btn_layout);
		add("Center", number_layout);
		add("South", input_layout);
		
		
		setSize(450, 200); //컨테이너 크기 설정
		setResizable(false); //크기 변경 불가	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X버튼을 누르면 종료
		setVisible(true); //화면에 표시
	}
	
	//해당 Url의 이미지 파일을 불러와서 BufferedImage로 반환
	public BufferedImage getUrlImage(String num){ 
		URL url;
		try {
			url = new URL("http://www.nlotto.co.kr/img/common_new/ball_" + num + ".png");
			BufferedImage img = null;
			try {
				img = ImageIO.read(url);
			} catch (IOException e) {
				System.out.println("E: Image Load failed!");
			}		
			return img;
		} catch (MalformedURLException e) {
			System.out.println("E: Connection Error!");
			return null;
		}
	}
	
	
	//문자열을 숫자만 추출해서 반환
	public String getNumberOnly(String data){
		return data.replaceAll("\\D","");
	}
	
	//문자열을 정수로 변환해서 반환
	public int StringToInt(String data){
		return Integer.parseInt(data);
	}
	
	
	//다른 회차로 이동 시, 텍스트 업데이트
	public void update(){
		if(Max_ep == 0){
			//첫 실행시 가장 최근의 회차번호를 저장함. 
			int i = Integer.parseInt(lotto_num[7]);
			Max_ep = i;
		}
		
		//lotto_num 배열의 7번 인덱스에는 추출된 회차번호가 기록되어있음
		print_ep.setText(lotto_num[7]+" 회차");
	}
	
	
	//인터넷에 연결하여 데이터를 받아오는 Thread 실행
	public void refresh(String num){
		Thread thread = new Thread(this);
		thread.start();
	}
	
	
	//프로그램의 중심을 차지하고있는 스레드(데이터를 받아옴)
	@Override
	public void run() {
		//스레드에서 데이터를 받아오는동안 버튼을 비활성화 시킴
		prev_btn.setEnabled(false);
		next_btn.setEnabled(false);
		refresh_btn.setEnabled(false);
		print_ep.setText("Loading..");
		System.out.print("Connecting..");
		try{
			//해당 주소로 접속하여 HTML 문서를 읽어옴
			Document doc = Jsoup.connect(base_url+lotto_num[7]).get();
			System.out.println(" OK!");
			
			//받아온 문서에서 필요한 부분만 파싱
			Elements contents = doc.select("h3.result_title strong");
			lotto_num[7] = contents.toString().replaceAll("\\D","");
			System.out.print(lotto_num[7] + "회 Data Loading.. ");
			
			contents = doc.select("p.number img");
			int i=0;
			
			//파싱한 데이터 중, 로또 번호만 다시 파싱하여 배열에 차례로 저장 
			for(Element e : contents){
				lotto_num[i++] = e.attr("alt");
				//System.out.println(lotto_num[i++]);
			}
			System.out.println("DONE!");
			
			//getUrlImage() 메소드를 통하여 인터넷에서 사진 불러옴
			img_1 = new ImageIcon(getUrlImage(lotto_num[0]));
			img_2 = new ImageIcon(getUrlImage(lotto_num[1]));
			img_3 = new ImageIcon(getUrlImage(lotto_num[2]));
			img_4 = new ImageIcon(getUrlImage(lotto_num[3]));
			img_5 = new ImageIcon(getUrlImage(lotto_num[4]));
			img_6 = new ImageIcon(getUrlImage(lotto_num[5]));
			img_7 = new ImageIcon(getUrlImage(lotto_num[6]));
			System.out.println("Image load DONE!");
			//불러온 이미지를 라벨에 넣음
			label_1.setIcon(img_1);
			label_2.setIcon(img_2);
			label_3.setIcon(img_3);
			label_4.setIcon(img_4);
			label_5.setIcon(img_5);
			label_6.setIcon(img_6);
			label_7.setIcon(img_7);
			update();
		}
		catch(Exception e){
			//예외 발생 시 실행할 로직
			System.out.println("E: Connection Error!");
			e.getStackTrace();
		}
		finally {
			/* 예외 여부와 상관없이 실행 
			 * 버튼을 다시 활성화 시킴 */
			prev_btn.setEnabled(true);
			next_btn.setEnabled(true);
			refresh_btn.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		new Main(); //메인 메소드에서 Gui를 생성하고 종료
	}
}