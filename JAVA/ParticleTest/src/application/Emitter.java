package application;

import java.util.List;

import javafx.scene.paint.Color;

// Emit : 방출하다
public abstract class Emitter {
	public abstract List<CircleParticle> emit(double x, double y, int count, double rotate, Color c);
}
