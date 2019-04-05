package edu.caltech.cs2.project01;

import edu.caltech.cs2.libraries.FakeBody;
import edu.caltech.cs2.libraries.IBody;
import edu.caltech.cs2.libraries.StdDraw;
import edu.caltech.cs2.libraries.Vector2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBodySimulation {
    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println(args[0] + " " + args[1] + " " + args[2]);
        double endTime = Double.parseDouble(args[0]);
        double timeIncrement = Double.parseDouble(args[1]);
        Scanner fileScanner = new Scanner(new File(args[2]));
        int numberOfBodies = Integer.parseInt(fileScanner.next());
        double universeRadius = Double.parseDouble(fileScanner.next());
        IBody[] bodies = readBodies(fileScanner, numberOfBodies);

        setupDrawing(universeRadius);
        for (int t = 0; t < endTime; t += timeIncrement) {
            drawStep(bodies);
            calculateStep(bodies, timeIncrement);
            StdDraw.pause(1);
        }
        printState(universeRadius, bodies);
    }

    public static IBody[] readBodies(Scanner in, int numBodies) {
        Body[] ret = new Body[numBodies];
        for (int x = 0; x < numBodies; x++) {
            ret[x] = new Body(in.nextDouble(), in.nextDouble(),
                    in.nextDouble(), in.nextDouble(), in.nextDouble(),
                    in.next());

        }
        return ret;

    }

    public static void calculateStep(IBody[] bodies, double dt) {
        /*
        for (IBody x : bodies) {
            x.calculateNewForceFrom(bodies);
            x.updatePosition(dt);
        }
        */
        for (int x = 0; x < bodies.length; x++) {
            bodies[x].calculateNewForceFrom(bodies);
        }
        for (int x = 0; x < bodies.length; x++) {
            bodies[x].updatePosition(dt);
        }
    }

    public static void setupDrawing(double radius) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-1 * radius, radius);
    }

    public static void printState(double radius, IBody[] bodies) {

        System.out.println(bodies.length);
        System.out.println(String.format("%11.2e", radius).trim());
        //System.out.println(radius);

        for (IBody x : bodies) {
            /*System.out.printf("\n%11.4e %11.4e %12s",
                    x.getCurrentPosition().getX(),
                    x.getCurrentPosition().getY(), x.getFileName()); */
            System.out.println(x.toString());
        }
    }

    public static void drawStep(IBody[] bodies) {
        /*for (IBody x : bodies) {
            StdDraw.picture(x.getCurrentPosition().getX(),
                    x.getCurrentPosition().getY(),
                    "data/images/" + x.getFileName());
        }*/
        for (int x = 0; x < bodies.length; x++) {
            Vector2D currentPos = bodies[x].getCurrentPosition();
            StdDraw.picture(currentPos.getX(), currentPos.getY(),
                    "data/images/" + bodies[x].getFileName());
        }
        StdDraw.show();
        StdDraw.picture(0, 0, "data/images/starfield.jpg");
    }
}