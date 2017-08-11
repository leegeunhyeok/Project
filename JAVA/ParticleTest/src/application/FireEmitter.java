package application;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

public class FireEmitter extends Emitter {

	@Override
	public List<CircleParticle> emit(double x, double y, int numParticles, double rotate, Color color) {
		List<CircleParticle> particles = new ArrayList<>();

		// numParticles개의 파티클 생성
		for (int i = 0; i < numParticles; i++) {
			CircleParticle p = new CircleParticle(x, y, new Point2D((Math.random() - 0.5) + rotate, Math.random() * 4),
					20, 3.5, color, BlendMode.ADD); // BlendMode.ADD = 기존 색상 곂치기
			particles.add(p);
		}
		return particles; // 생성된 파티클 리스트 반환
	}
}
