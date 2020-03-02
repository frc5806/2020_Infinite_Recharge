package frc.robot;
interface AutoFireData extends RobotData{
    final double maxTRSpeed = 0;
    final int visionBound = 0;
    default double dToBound() {
        return Data.d;
    }
    default double toSpeed(){
        return (Math.atan(Math.toRadians((Data.d+radToCam)/Data.roboVel))/360)*(2*Math.PI*radToCam);
    }
}