package org.firstinspires.ftc.teamcode.mecanismos;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;
import java.util.List;

public class AprilTagWebcam {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    private List<AprilTagDetection> detectedTags = new ArrayList<>();

    private Telemetry telemetry;

    double erroX = 0;
    int larguraDaImagem = 640;

    public void init(HardwareMap hwMap, Telemetry telemetry){
        this.telemetry = telemetry;

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hwMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(640,480));
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();
    }

    public void update(){
        detectedTags = aprilTagProcessor.getDetections();

        for (AprilTagDetection detection : detectedTags){
            if (detection instanceof AprilTagSingleDetection){
                AprilTagSingleDetection singleDetection = (AprilTagSingleDetection) detection;

                telemetry.addData("Tag individual", "ID: %d", singleDetection.id);
                if (singleDetection.ftcPose != null) {
                    telemetry.addData(
                            "Distância da tag " + singleDetection.id,
                            "%.1f cm",
                            singleDetection.ftcPose.range
                    );
                }
            } else if (detection instanceof AprilTagClusterDetection) {
                AprilTagClusterDetection clusterDetection = (AprilTagClusterDetection) detection;
                if(clusterDetection.ftcPose != null) {
                    telemetry.addData("Distância do cluster " + clusterDetection.metadata.name,
                            "%.1f cm",
                            clusterDetection.ftcPose.range);
                }
            }
        }
    }

    public List<AprilTagDetection> getDetectedTags(){
        return detectedTags;
    }

    public double erroAngularGraus() {
        for (AprilTagDetection detection : aprilTagProcessor.getDetections()) {
            if (detection.ftcPose != null) {
                return detection.ftcPose.bearing;
            }
        }

        return Double.NaN;
    }


/*
    public AprilTagDetection getTagBySpecificId(int id){
        for (AprilTagDetection detection : detectedTags){
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection singleDetection = (AprilTagSingleDetection) detection;
                if (singleDetection.id == id) {
                    return singleDetection;
                }
            }
        }
        return null;
    }
    public AprilTagClusterDetection aprilTagClusterDetection(String name){
        for (AprilTagDetection detection : detectedTags){
            if (detection instanceof AprilTagClusterDetection){
                AprilTagClusterDetection clusterDetection = (AprilTagClusterDetection) detection;

                if(clusterDetection.metadata.name.equals(name)){
                    return clusterDetection;
                }
            }
        }
        return null;
    }

 */
}
