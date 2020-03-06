package frc.robot;
interface AutoFireData extends RobotData{
    final double maxTRRPM = 10;
    final int visionBound = 320;
    default double dToBound() {
        return 10;
    }
    default double toSpeed(){
        return (Math.atan(Math.toRadians((Data.d+radToCam)/Data.roboVel))/360)*(2*Math.PI*radToCam);
    }
}