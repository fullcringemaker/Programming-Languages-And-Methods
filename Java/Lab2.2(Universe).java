-------------------------------------------------- Universe.java --------------------------------------------------
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


-------------------------------------------------- Main.java --------------------------------------------------
public class Main {
    public static void main(String[] args) {
        Particle particle1 = new Particle(20, -10, 0, 0);
        Particle particle2 = new Particle(20, 10, 0, 0);
        Particle particle3 = new Particle(20, 0, -10, 0);
        Particle particle4 = new Particle(20, 0, 10, 0);
        Particle particle5 = new Particle(20, 0, 0, 0);
        Universe.addParticle(particle1);
        Universe.addParticle(particle2);
        Universe.addParticle(particle3);
        Universe.addParticle(particle4);
        Universe.addParticle(particle5);
        System.out.println("Particles in the universe: " + Universe.getParticleCount());
        double[] totalForce = Universe.calcTotalForceOnParticle(particle5);
        System.out.println("Total gravitational force on the particle 1: (" + totalForce[0] + ", " + totalForce[1] + ", " + totalForce[2] + ")");
    }
}


-------------------------------------------------- Particle.java --------------------------------------------------
class Particle {
    private double mass;
    private double x, y, z;
    public Particle(double mass, double x, double y, double z) {
        this.mass = mass;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public double[] calcForceFrom(Particle otherParticle) {
        double G = 6.674 * Math.pow(10, -11);
        double dx = otherParticle.x - this.x;
        double dy = otherParticle.y - this.y;
        double dz = otherParticle.z - this.z;
        double rSquared = dx * dx + dy * dy + dz * dz;
        double forceMagnitude = (G * this.mass * otherParticle.mass) / rSquared;
        double forceX = forceMagnitude * dx / Math.sqrt(rSquared);
        double forceY = forceMagnitude * dy / Math.sqrt(rSquared);
        double forceZ = forceMagnitude * dz / Math.sqrt(rSquared);
        return new double[]{forceX, forceY, forceZ};
    }
}
