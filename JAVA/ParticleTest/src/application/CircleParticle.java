package application;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Paint;

public class CircleParticle extends Particle {
	private double radius; // 반지름

	public CircleParticle(double x, double y, Point2D velociry, double radius, double expireTime, Paint color,
			BlendMode blendMode) {
		this.x = x;
		this.y = y;
		this.velocity = velociry;
		this.radius = radius;
		this.color = color;
		this.blendMode = blendMode;
		this.decay = 0.1 / expireTime;
	}

	@Override
	public boolean isAlive() {
		return life > 0;
	}

	@Override
	public void update() {
		x += velocity.getX(); // X만큼 이동 (좌, 우)
		y -= velocity.getY(); // Y만큼 위로
		life -= decay;
	}

	@Override
	public void render(GraphicsContext g) {
		g.setGlobalAlpha(life); // 남은 life 만큼 알파값(투명도) 설정
		g.setGlobalBlendMode(blendMode);
		g.setFill(color); // 색깔
		g.fillOval(x, y, radius, radius); // 원 그리기
	}
}
