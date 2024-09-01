import java.util.ArrayList;
class Universe {
    private static ArrayList<Particle> particles = new ArrayList<>();
    public static void addParticle(Particle particle) {
        particles.add(particle);
    }
    public static int getParticleCount() {
        return particles.size();
    }
    public static double[] calcTotalForceOnParticle(Particle particle) {
        double totalForceX = 0.0;
        double totalForceY = 0.0;
        double totalForceZ = 0.0;
        for (Particle otherParticle : particles) {
            if (otherParticle != particle) {
                double[] force = particle.calcForceFrom(otherParticle);
                totalForceX += force[0];
                totalForceY += force[1];
                totalForceZ += force[2];
            }
        }
        return new double[]{totalForceX, totalForceY, totalForceZ};
    }
}
