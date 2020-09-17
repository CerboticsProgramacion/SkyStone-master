package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;


@Autonomous(name = "Concept: time", group = "Concept")
public class time2 extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Skystone";
    private static final String LABEL_SECOND_ELEMENT = "Stone";

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor atrasIzquierdo = null;
    public DcMotor atrasDerecho = null;

    public Servo servo1;
    public Servo servo2;

    public Servo autonomo;

    double n;

    private static final String VUFORIA_KEY ="AeBys8T/////AAABmS6wmLBYS0o+tkLaWPFNnbUdk/Z/2hj5ym7KHUguZVgx+sktY60a3nqBsc2R2pZsPgtHeGTg/0sPAMPDWhGpTl2leNtZsTA0Zvhg0+jE/crjjI4bLA0kk0F5P7Sh0UyIRSpOAJZFn/jRQ8FTBnNiDeN2o09371yez42PQqSxXoarI2LmOos5h+pysfvuDtsr83DzpiDD0erd1ND7UqDFnzwEWQ9eXB2UDkO/URm+VEFCFopNmzzUt2kqzQgy8JBcu0fXWvmY/QnK2S2oyrm9oJcBGEPgV/LX1uhJ/q+pGO7Fv1wB+aRdun8hEwlF2XBs7leVSa711zEYogyPqDvsAtaPp2x7Sr19SdCjEpA7q3Ko";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        leftDrive = hardwareMap.get(DcMotor.class,"m0");
        rightDrive = hardwareMap.get(DcMotor.class,"m1");
        atrasIzquierdo = hardwareMap.get(DcMotor.class,"m2");
        atrasDerecho = hardwareMap.get(DcMotor.class,"m3");
        servo1 = hardwareMap.get(Servo.class,"s1");
        servo2 = hardwareMap.get(Servo.class,"s2");
        autonomo = hardwareMap.get(Servo.class,"s6");

        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        atrasIzquierdo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        atrasDerecho.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        atrasIzquierdo.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        atrasDerecho.setDirection(DcMotor.Direction.FORWARD);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        runtime.reset();

//MOVERSE AL FRENTE
        n += 800;
        while (runtime.milliseconds() < n) {
            front(1);
        }
        absZero();

        n += 1000;
        while (runtime.milliseconds() < n){
            right(1);
        }

        n += 3000;
        while (runtime.milliseconds() < n){
            back(.2);
        }

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());

                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                        }
                        telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public void absZero() {
        leftDrive.setPower(0);
        atrasIzquierdo.setPower(0);
        rightDrive.setPower(0);
        atrasDerecho.setPower(0);
    }

    public void back(double velocity) {
        leftDrive.setPower(velocity);
        atrasIzquierdo.setPower(velocity);
        rightDrive.setPower(velocity);
        atrasDerecho.setPower(velocity);
    }

    public void front(double velocity) {
        leftDrive.setPower(-velocity);
        atrasIzquierdo.setPower(-velocity);
        rightDrive.setPower(-velocity);
        atrasDerecho.setPower(-velocity);
    }

    public void left(double velocity) {
        leftDrive.setPower(-velocity);
        atrasIzquierdo.setPower(velocity);
        rightDrive.setPower(velocity);
        atrasDerecho.setPower(-velocity);
    }

    public void right(double velocity) {
        leftDrive.setPower(velocity);
        atrasIzquierdo.setPower(-velocity);
        rightDrive.setPower(-velocity);
        atrasDerecho.setPower(velocity);
    }

    public void rightTurn(double velocity) {
        leftDrive.setPower(velocity);
        atrasIzquierdo.setPower(velocity);
        rightDrive.setPower(-velocity);
        atrasDerecho.setPower(-velocity);
    }

    public void leftTurn(double velocity) {
        leftDrive.setPower(-velocity);
        atrasIzquierdo.setPower(-velocity);
        rightDrive.setPower(velocity);
        atrasDerecho.setPower(velocity);
    }

    public void setServos(int positionL, int positionR) {
        servo1.setPosition(positionL);
        servo2.setPosition(positionR);
    }

}

