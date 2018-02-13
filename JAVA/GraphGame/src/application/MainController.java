package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MainController implements Initializable {
	@FXML Text ownCoinText, multipleText, payText, ownCoinTextInfo, totalGame, winRate;
	@FXML LineChart<String, Double> graph;
	@FXML CategoryAxis xAxis;
	@FXML NumberAxis yAxis;
	@FXML TextField multipleInput, betInput;
	@FXML Button startBtn, resetBtn;
	
	private int coin, betCoin, ownCoin, win, lose;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		readFile();
		graph.setAnimated(false); // 애니메이션 효과 X
		graph.getXAxis().setTickLabelsVisible(false); // X축 라벨 숨기기 
		
		multipleInput.setOnKeyReleased((event)-> {
			computeCoin(); // 키가 때지면 예상 수익 계산 
		});
		
		betInput.setOnKeyReleased((event)-> {
			computeCoin(); 
		});
		
		startBtn.setOnAction((event)-> {		
			start();
		});
		
		resetBtn.setOnAction((event)-> { // 리셋버튼 
			ownCoin = 1000; // 기본값으로 설정 후 
			win = 0;
			lose = 0;
			saveFile(); // 파일 저장 
			readFile(); // 불러와서 반영 
		});
	}
	
	// 게임 시작 
	private void start() {
		double betMultiple = Double.parseDouble(multipleInput.getText()); // 예측 배율을 double 타입으로 변환
		final double computedMultiple = getMultiple(); // 랜덤 배수 가져오기 
		disableControl(true); // 버튼, 입력창 등 모두 비활성 
		
		XYChart.Series<String, Double> series = new Series<String, Double>();
		graph.getData().clear(); // 그래프의 데이터 지우기 
		graph.getData().add(series); // 새로운 데이터 등록 
		
		// 스레드 생성 
		Thread t = new Thread(() -> {
			int i = 0;
			double num = 0; // 배수 (0부터 시작)
			DecimalFormat df = new DecimalFormat("0.00"); // 배율은 항상 소숫점 2자리까지 
			while(true) {
				try {
					Thread.sleep(80);
					series.getData().add(new XYChart.Data(i++ + "", num));
					num += ((num+1)*0.01);
					multipleText.setText(df.format(num) + "x");	
					System.out.println(num);
					if(num > computedMultiple) break; // 지정된 배율까지 가면 종료
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// 만약 유저 배율이 더 작을경우 배당금 지급 (적중)
			if(Double.parseDouble(df.format(num)) >= betMultiple) {
				System.out.println("Win!: +" + coin);
				ownCoin += coin;
				win++;
			} else { // 유저 배율이 더 크면 실패 (배팅한 코인 사라짐)
				System.out.println("Lose!: -" + betCoin);
				ownCoin -= betCoin;
				lose++;
			}
			saveFile(); // 파일 저장 후 불러와서 반영
			readFile();
			clearControl(); // 입력창의 데이터 공백으로 지움 
			disableControl(false); // 버튼, 입력창 비활성화 해제 
		});
		t.setDaemon(true); // 데몬스레드 설정 
		t.start(); // 스레드 시작 
	}
	
	// 버튼, 입력창 비활성화 여부 (true: 비활성, false: 활성)
	private void disableControl(final boolean disable) {
		multipleInput.setDisable(disable);
		betInput.setDisable(disable);
		startBtn.setDisable(disable);
	}
	
	// 게임 초기상태로 설정 
	private void clearControl() {
		startBtn.setDisable(true);
		multipleInput.setText(null);
		betInput.setText(null);
		payText.setText("0");
	}
	
	// 랜덤 배율 가져오기 
	private double getMultiple() {
		int n = (int)Math.round(Math.random() * 10); // 0 ~ 9
		if(n > 8) {
			return Math.random() * 100; 
		} else if(n > 6) {
			return Math.random() * 15; 
		} else {
			return Math.random() * 3; 
		}
	}
	
	// 예상 수익 계산 
	private void computeCoin() { 
		try {
			double multiple = Double.parseDouble(multipleInput.getText());
			int coin = Integer.parseInt(betInput.getText());
			betCoin = coin;
			if(ownCoin-betCoin >= 0) {
				this.coin = (int)Math.round(multiple * coin);
				payText.setText(this.coin + "");
				startBtn.setDisable(false);
				System.out.println("예상 수익: " + this.coin + " (" + multiple + " * " + coin + ")");
			} else {
				payText.setText("코인 초과");
			}
		} catch(NumberFormatException e) {
			startBtn.setDisable(true);
		}
	}
	
	// 파일 불러오기 
	private void readFile() {
		System.out.print("파일 불러오는 중.. ");
		try {
			BufferedReader in = new BufferedReader(new FileReader("user.dat"));
			ownCoin = Integer.parseInt(in.readLine());
			win = Integer.parseInt(in.readLine());
			lose = Integer.parseInt(in.readLine());
			ownCoinText.setText(ownCoin + "");
			ownCoinTextInfo.setText(ownCoin + "");
			
			int total = win + lose;
			totalGame.setText(total + "");
			if(total > 0) {
				winRate.setText(Math.round((double)win/total*100) + " %");
			} else {
				winRate.setText("0 %");
			}
			in.close();
			System.out.println("성공");
		} catch(FileNotFoundException e) {
			System.out.println("파일이 없습니다");
			createNewFile();
		} catch(IOException e) {
			System.out.println("실패");
			ownCoin = 1000;
			win = 0;
			lose = 0;
		}
	}
	
	// 파일 저장하기 
	private void saveFile() {
		System.out.print("파일 저장하는 중.. ");
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("user.dat"));
			out.write("" + ownCoin);
			out.newLine();
			out.write("" + win);
			out.newLine();
			out.write("" + lose);
			out.close();
			System.out.println("성공");
		} catch(FileNotFoundException e) {
			System.out.println("파일이 없습니다");
			createNewFile();
		} catch(IOException e) {
			System.out.println("실패");
		}
	}
	
	// 새 파일 생성 
	private void createNewFile() {
		System.out.print("새 파일 생성하는 중.. ");
		ownCoin = 1000;
		ownCoinText.setText("1000");
		ownCoinTextInfo.setText("1000");
		try {
			BufferedWriter newfile = new BufferedWriter(new FileWriter("user.dat"));
			newfile.write("1000");
			newfile.newLine();
			newfile.write("0");
			newfile.newLine();
			newfile.write("0");
			newfile.close();
			System.out.println("성공");
		} catch (IOException e1) {
			System.out.println("실패");
		}
	}
}
