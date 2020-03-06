package frc.robot;

import com.kauailabs.navx.frc.AHRS;

final class Auto{

    private AutoDrivetrain drive;

    public Auto(Drivetrain drive, AHRS navX){
        this.drive = new AutoDrivetrain(drive, navX);
    }
    public void choose(String in, boolean sOi){

    }
    private void store(int in){
        switch(in){
            case 1:

                return;
            case 2:

                return;
            case 3:

                return;
            case 4:

                return;

            case 5:

                return;
            case 6:

                return;
            default:

                return;
        }
    }
    
}