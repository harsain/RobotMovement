package com.harsain.robotmovement;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by harsain on 7/1/17.
 */
public class Robot {
    private int x = 0;
    private int y = 0;

    private boolean isFirstCommand = true;
    private final int xIndexMinLimit = 0;
    private final int yIndexMinLimit = 0;
    private final int xIndexMaxLimit = 4;
    private final int yIndexMaxLimit = 4;

    private DirectionEnum direction;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }

    public void command(Command command) throws InvalidPositionAttributeException, InvalidFirstCommandException {

        CommandNameEnum currentCommandName = CommandNameEnum.valueOf(command.getName());
        if (this.isFirstCommand && !currentCommandName.equals(CommandNameEnum.PLACE)) {
            throw new InvalidFirstCommandException("First command has to be PLACE int,int,string");
        }
        switch (currentCommandName) {
            case PLACE:
                ArrayList arguments = command.getArguments();
                Integer x = Integer.parseInt(arguments.get(0).toString());
                Integer y = Integer.parseInt(arguments.get(1).toString());
                String direction = arguments.get(2).toString();
                if (x > xIndexMaxLimit || x < xIndexMinLimit || y > yIndexMaxLimit || y < yIndexMinLimit) {
                    throw new InvalidPositionAttributeException("Either x or y is out of limits");
                }
                this.place(x, y, direction);
                if (this.isFirstCommand) {
                    this.isFirstCommand = false;
                }
                break;
            case REPORT:
                System.out.println(this.report());
                break;
            case LEFT:
                this.updateDirection("LEFT");
                break;
            case RIGHT:
                this.updateDirection("RIGHT");
                break;
            case MOVE:
                this.calculatePositionChange();
                break;
            default:
                break;
        }
    }

    private void place(int x, int y, String direction) {
        if (this.validatePosition(x, y) && this.validateDirection(direction)) {
            this.setX(x);
            this.setY(y);
            this.setDirection(DirectionEnum.valueOf(direction));
        }
    }

    private String report() {
        return String.format("%d, %d, %s", this.getX(), this.getY(), this.getDirection().toString());
    }

    private void calculatePositionChange() {
        HashMap<String, Integer> positionChange = new HashMap<String, Integer>();

        switch (this.getDirection()) {
            case NORTH:
                if (this.getY() < this.yIndexMaxLimit) {
                    this.setY(this.getY() + 1);
                }
                break;
            case SOUTH:
                if (this.getY() > this.yIndexMinLimit) {
                    this.setY(this.getY() - 1);
                }
                break;
            case EAST:
                if (this.getX() < xIndexMaxLimit) {
                    this.setX(this.getX() + 1);
                }
                break;
            case WEST:
                if(this.getX() > xIndexMinLimit) {
                    this.setX(this.getX() - 1);
                }
                break;
        }
    }

    /**
     * this method updates the direction based on the current direction
     * and based on the turn
     * @param turn
     */
    private void updateDirection(String turn) {
        if (turn.equalsIgnoreCase("LEFT")) {
            if (this.direction.toString().equalsIgnoreCase("NORTH")) {
                this.setDirection(DirectionEnum.WEST);
            } else if (this.direction.toString().equalsIgnoreCase("WEST")) {
                this.setDirection(DirectionEnum.SOUTH);
            } else if (this.direction.toString().equalsIgnoreCase("SOUTH")) {
                this.setDirection(DirectionEnum.EAST);
            } else if (this.direction.toString().equalsIgnoreCase("EAST")) {
                this.setDirection(DirectionEnum.NORTH);
            }
        } else if(turn.equalsIgnoreCase("RIGHT")) {
            if (this.direction.toString().equalsIgnoreCase("NORTH")) {
                this.setDirection(DirectionEnum.EAST);
            } else if (this.direction.toString().equalsIgnoreCase("EAST")) {
                this.setDirection(DirectionEnum.SOUTH);
            } else if (this.direction.toString().equalsIgnoreCase("SOUTH")) {
                this.setDirection(DirectionEnum.WEST);
            } else if (this.direction.toString().equalsIgnoreCase("WEST")) {
                this.setDirection(DirectionEnum.NORTH);
            }
        }
    }

    /**
     * validates the position
     * @param x
     * @param y
     * @return
     */
    private boolean validatePosition(int x, int y) {

        if ((x >= 0 || x < 5) && (y >= 0 || y < 5) ){
            return true;
        }
        return false;
    }

    private boolean validateDirection(String direction) {
        if (DirectionEnum.contains(direction)) {
            return true;
        }

        return false;
    }
}
