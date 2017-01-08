package com.harsain;

public class App 
{
    public static void main( String[] args )
    {
        com.harsain.robotmovement.App robotMovement = com.harsain.robotmovement.App.getInstance();

        robotMovement.start();
    }
}
