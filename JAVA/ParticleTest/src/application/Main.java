package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	private int red = 255, green = 70, blue = 20;
	private double rotate = 0;

	private Emitter emitter = new FireEmitter(); // 파티클 생성하는 객체

	private List<CircleParticle> particles = new ArrayList<>();

	private GraphicsContext g;

	// 프레임마다 호출되는 메소드
	private void onUpdate() {
		g.setGlobalAlpha(1.0); // 불투명도, 0.0 투명 ~ 1.0 불투명
		g.setGlobalBlendMode(BlendMode.SRC_OVER); // 기존 위에 덮어씌우기
		g.setFill(Color.BLACK); // 검정색으로 설정
		g.fillRect(0, 0, 600, 600); // 사각형 그리기

		// x, y, 갯수, 방향, 색상
		particles.addAll(emitter.emit(290, 300, 10, rotate, Color.rgb(red, green, blue))); // Emit 한 파티클 리스트를 모두 추가

		for (Iterator<CircleParticle> it = particles.iterator(); it.hasNext();) {
			CircleParticle p = it.next();
			p.update();

			if (!p.isAlive()) { // 파티클 생존시간이 없으면
				it.remove(); // 해당 파티클 삭제
				continue;
			}
			p.render(g);
		}
	}

	// Context 생성 (밑그림)
	private Parent createContext() {
		Pane root = new Pane();
		root.setPrefSize(600, 600); // Pane의 폭, 높이를 600px로 설정

		Canvas canvas = new Canvas(600, 600); // 캔버스 생성
		g = canvas.getGraphicsContext2D();

		Slider r = new Slider(0, 255, 255); // red 슬라이더
		r.relocate(10, 10);
		r.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				red = newValue.intValue();
			}
		});

		Slider g = new Slider(0, 255, 70); // green 슬라이더
		g.relocate(10, 30);
		g.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				green = newValue.intValue();
			}
		});

		Slider b = new Slider(0, 255, 20); // blue 슬라이더
		b.relocate(10, 50);
		b.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				blue = newValue.intValue();
			}
		});

		Slider ro = new Slider(-5, 5, 0); // 방향 슬라이더
		ro.relocate(10, 80);
		ro.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				rotate = newValue.doubleValue();
			}
		});

		root.getChildren().add(canvas);
		root.getChildren().addAll(r, g, b, ro);
		return root;
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(new Scene(createContext()));
		primaryStage.setTitle("Paticles Effect");
		primaryStage.show();

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				// 매 프레임마다 호출됨
				onUpdate();
			}
		};
		timer.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
