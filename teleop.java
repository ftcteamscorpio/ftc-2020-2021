package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp
public class teleop extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        DcMotor tl = hardwareMap.get(DcMotor.class, "tl");
        DcMotor tr = hardwareMap.get(DcMotor.class, "tr");
        DcMotor bl = hardwareMap.get(DcMotor.class, "bl");
        DcMotor br = hardwareMap.get(DcMotor.class, "br");
        motorblock block = new motorblock(tl, tr, bl, br);
        DcMotor intake = hardwareMap.get(DcMotor.class,"intake");
        DcMotor transfer = hardwareMap.get(DcMotor.class, "transfer");
        DcMotor shooter = hardwareMap.get(DcMotor.class, "shooter");
        DcMotor arm = hardwareMap.get(DcMotor.class, "arm");
        Servo claw1 = hardwareMap.get(Servo.class, "claw1");
        Servo claw2 = hardwareMap.get(Servo.class, "claw2");

        boolean intakeMotorStatus = false;
        boolean intakeButtonStatus = false;
        boolean shooterMotorStatus = false;
        boolean shooterButtonStatus = false;

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.left_stick_y < 0) { //goes forward
                block.forward(1);
            }
            if (gamepad1.left_stick_y > 0) { //goes backward
                block.backward(1);
            }
            if (gamepad1.left_stick_x < 0) { //goes left
                block.left(1);
            }
            if (gamepad1.left_stick_x > 0) { //goes right
                block.right(1);
            }
            if (gamepad1.right_stick_x < 0) { //turns left
                block.leftturn(0.7);
            }
            if (gamepad1.right_stick_x > 0) { //turns right
                block.rightturn(0.7);
            } else {
                block.stop();
            }
            if (gamepad1.left_trigger == 1 && !intakeButtonStatus) {
                intakeMotorStatus = !intakeMotorStatus;
                intakeButtonStatus = true;
            }
            if (gamepad1.left_trigger == 0) intakeButtonStatus = false;
            intake.setPower(intakeMotorStatus ? 1 : 0);
            transfer.setPower(intakeMotorStatus ? 0.6 : 0); //toggles intake and transfer

            if (gamepad1.left_bumper) { //reverses intake and transfer
                intake.setPower(-1);
                transfer.setPower(-0.6);
            }

            if (gamepad1.right_trigger == 1 && !shooterButtonStatus){
                shooterMotorStatus = !shooterMotorStatus;
                shooterButtonStatus = true;
            }
            if (gamepad1.right_trigger == 0) shooterButtonStatus = false;
            shooter.setPower(shooterMotorStatus ? -0.8 : 0); //toggles shooter

            if(gamepad1.right_bumper) { //reverses shooter
                shooter.setPower(0.8);
            }
            if(gamepad1.dpad_up) {
                arm.setPower(1);
            } else arm.setPower(0);

            if(gamepad1.dpad_down){
                arm.setPower(-1);
            } else arm.setPower(0);

            if(gamepad1.a){
                claw1.setPosition(1);
                claw2.setPosition(0);
            }
            if(gamepad1.y){
                claw1.setPosition(0);
                claw2.setPosition(1);
            }

        }
    }
}

