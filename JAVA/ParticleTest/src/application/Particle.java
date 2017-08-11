package application;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Paint;

public abstract class Particle {
	protected double x;
	protected double y;

	protected Point2D velocity; // 속도
	protected double life = 1.0; // 생명 (시간)
	protected double decay; // Life 감소값 설정 (expiteTime: 높을수록 파티클 오래 지속됨)

	protected Paint color; // 색상
	protected BlendMode blendMode; //

	protected abstract boolean isAlive(); // 파티클 생존여부

	protected abstract void update(); // 파티클 업데이트

	protected abstract void render(GraphicsContext g); // 파티클 그리기
}
