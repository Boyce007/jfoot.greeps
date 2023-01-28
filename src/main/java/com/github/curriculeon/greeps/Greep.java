package com.github.curriculeon.greeps;

import com.github.git_leon.RandomUtils;
import greenfoot.Actor;

/**
 * A Greep is an alien creature that likes to collect tomatoes.
 *
 * @author (your name here)
 * @version 0.1
 */
public class Greep extends Creature {
    /**
     * Create a creature at its ship.
     *
     * @param ship
     */
    public Greep(Spaceship ship) {
        super(ship);
        setImage(getCurrentImage());
    }

    @Override
    protected void behave() {
        if (isCarryingTomato()) {
            if (isAtShip()) {
                dropTomato();
            } else {
                turnTowardsHome();
            }
        }
        if (isStuck())  {
            turnRandomly(0,360,3);
        } else {
            seekTomatoPile();
        }


        if (!canMove()) {
        } else {
            seekTomatoPile();
        }
        if (isAtTomatoes()) {
            waitForTomatoLoadingAssistance();
            checkFood();
        }
        if (isReturningToShip() || isStuck()) {
            turnRandomDegrees(17,300);
            move(36);
        }
    }
    public Boolean isStuck() {
        if (isAtWater() || isAtEdge() || !canMove()) {
            return true;
        }
        return false;
    }


    private Boolean isToLeft(Actor actor) {
        int currentRotation = getRotation();
        turnTowards(actor);
        int relativeAngle = getRotation();
        if (relativeAngle > 180)
            return true;
        setRotation(currentRotation);
        return false;
    }

    private GreepSpit getIntersectingSpit() {
        return (GreepSpit) getOneIntersectingObject(GreepSpit.class);
    }

    private boolean isAtSpit(String colorName) {
        GreepSpit potentialSpit = (GreepSpit) super.getOneIntersectingObject(GreepSpit.class);
        if (potentialSpit != null) {
            return potentialSpit.getColor().equalsIgnoreCase(colorName);
        }
        return false;
    }

    public Boolean isWaitingForAssistance() {
        return isAtTomatoes() && !super.isCarryingTomato();
    }


    public Boolean isWaitingToAssist() {
        if (isAtTomatoes()) {
            for (Greep greep : getSurroundingTomatoPile().getIntersectingObjects(Greep.class)) {
                if (!greep.isCarryingTomato()) {
                    return true;
                }
            }
        }
        return false;
    }


    public void waitForTomatoLoadingAssistance() {
        turnTowards(getSurroundingTomatoPile());
        move();
        loadTomato();
    }


    public Boolean isReturningToShip() {
        return isCarryingTomato();
    }


    public void returnToShip() {
        super.turnTowardsHome(3);
        super.move();
    }


    public Boolean shouldSeekTomatoPile() {
        return !isCarryingTomato();
    }


    public void seekTomatoPile() {
        move();
    }


    public void turnRandomDegrees() {
        turnRandomDegrees(15, 90);
    }


    public void turnRandomDegrees(int minimumTurn, int maximumTurn) {
        turn(RandomUtils.createInteger(minimumTurn, maximumTurn));
    }


    public void turnRandomly(int minimumTurn, int maximumTurn, float likelihoodOfTurn) {
        if (minimumTurn > maximumTurn) {
            Integer temp;
            temp = maximumTurn;
            maximumTurn = minimumTurn;
            minimumTurn = temp;
        }
        if (RandomUtils.createBoolean(likelihoodOfTurn)) {
            turnRandomDegrees(minimumTurn, maximumTurn);
        }
    }

    /**
     * Is there any food here where we are? If so, try to load some!
     */

    public void checkFood() {
        // check whether there's a tomato pile here
        if (isAtTomatoes()) {
            loadTomato();
            // Note: this attempts to load a tomato onto *another* Greep. It won't
            // do anything if we are alone here.
        }
    }


    /**
     * This method specifies the image we want displayed at any time. (No need
     * to change this for the competition.)
     */

    public String getCurrentImage() {
        if (isCarryingTomato())
            return "greep-with-food.png";
        else
            return "greep.png";
    }

    /**
     * Create a Greep with its home space ship.
     */

    public static String getAuthorName() {
        return "Daniel";
    }
}