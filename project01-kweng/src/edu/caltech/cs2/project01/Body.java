package edu.caltech.cs2.project01;

import edu.caltech.cs2.libraries.IBody;
import edu.caltech.cs2.libraries.Vector2D;

public class Body implements IBody<Body> {

    private Vector2D position;
    private Vector2D velocity;
    private final double mass;
    private final String filename;
    private Vector2D netForce;

    private static final double G = 6.67e-11;

    public Body(Vector2D position, Vector2D velocity, double mass,
                String filename) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.filename = filename;
        this.netForce = new Vector2D(0, 0);
    }

    public Body(double xPos, double yPos, double xVel, double yVel,
                double mass, String filename) {
        this(new Vector2D(xPos, yPos), new Vector2D(xVel, yVel), mass,
                filename);
    }

    public Body(double xPos, double yPos, double xVel, double yVel) {
        this(new Vector2D(xPos, yPos), new Vector2D(xVel, yVel), 0, null);
    }

    private double distanceTo(Body other) {
        double yDelta =
                (getCurrentPosition().getY() - other.getCurrentPosition().getY());
        double xDelta =
                (getCurrentPosition().getX() - other.getCurrentPosition().getX());
        return Math.sqrt(Math.pow(yDelta, 2) + Math.pow(xDelta, 2));

    }
    /*
    private double angleBetween(Body other) {
        // Returns angle, in radians, between two bodies
        //System.out.println("marker one");
        double yDelta =
                (getCurrentPosition().getY() - other.getCurrentPosition().getY());
        double xDelta =
                (getCurrentPosition().getX() - other.getCurrentPosition().getX());
        System.out.println("y " + yDelta + " x " + xDelta);
        //System.out.println(Math.atan(yDelta / xDelta);
        return Math.atan(yDelta / xDelta);
    }
    */

    private Vector2D getForceFrom(Body other) {
        double r = distanceTo(other);
        double yDelta =
                (getCurrentPosition().getY() - other.getCurrentPosition().getY());
        double xDelta =
                (getCurrentPosition().getX() - other.getCurrentPosition().getX());
        /*
        if (distanceTo(other) == 0.0) {
            return new Vector2D(0, 0);
        }
        */
        //System.out.println("marker two");
        double force = (G * mass * other.mass) / (Math.pow(r, 2));
        //double angle = angleBetween(other);
        //System.out.println(angle);
        //double forceX = force * Math.cos(angle);
        //double forceY = force * Math.sin(angle);
        double forceX = force * xDelta / r;
        double forceY = force * yDelta / r;
        //System.out.println("Force x " + forceX + " force y" + forceY);
        return new Vector2D(-1 * forceX, -1 * forceY);
    }
    public void calculateNewForceFrom(Body[] bodies) {
        //System.out.println("marker three");
        Vector2D total = new Vector2D(0, 0);
        for (int x = 0; x < bodies.length; x++) {
            if (!(this.equals(bodies[x]))) {
                total = total.add(getForceFrom(bodies[x]));
            }
        }
        this.netForce = total;
    }

    public void updatePosition(double dt) {
        double acclX = this.netForce.getX() / this.mass;
        double acclY = this.netForce.getY() / this.mass;
        //System.out.println("acclX = " + acclX + " acclY = " + acclY);
        this.velocity = velocity.add(new Vector2D(acclX * dt, acclY * dt));
        this.position = position.add(new Vector2D(this.velocity.getX() * dt,
                this.velocity.getY() * dt));
    }

    public Vector2D getCurrentPosition() {
        return position;
    }

    public String getFileName() {
      return this.filename;
    }

    public String toString() {
        return String.format("%11.4e %11.4e %11.4e %11.4e %11.4e %12s",
                getCurrentPosition().getX(), getCurrentPosition().getY(),
                velocity.getX(), velocity.getY(),
                mass, getFileName());
    }
}