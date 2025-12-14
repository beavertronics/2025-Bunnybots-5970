package frc.robot

import kotlin.math.*
import beaverlib.utils.Sugar.within
import beaverlib.utils.Units.Electrical.volts
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.button.CommandJoystick
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.commands.subsytems.MoveIntake
import frc.robot.commands.subsytems.RunIntake
import frc.robot.commands.subsytems.RunPreindexer
import frc.robot.commands.swerve.TeleopDriveCommand
import frc.robot.subsystems.Drivetrain

/*
Sets up the operator interface (controller inputs), as well as
setting up the commands for running the drivetrain and the subsystems
 */

/**
 * class for managing systems and inputs
 */
object TeleOp {
    val teleOpDrive: TeleopDriveCommand =
        TeleopDriveCommand(
            { OI.driveForwards },
            { OI.driveStrafe },
            { OI.rotateRobot },
            { OI.toggleFieldOriented },
            { OI.slowMode }
        )

    init {
         Drivetrain.defaultCommand = teleOpDrive
    }

    /**
     * configures things to run on specific inputs
     */
    fun configureBindings() {
         OI.lowerIntake.whileTrue(MoveIntake(DoubleSolenoid.Value.kForward))
         OI.raiseIntake.whileTrue(MoveIntake(DoubleSolenoid.Value.kReverse))
         OI.runIntake.whileTrue(RunIntake(1.0.volts))
         OI.runPreindexer.whileTrue(RunPreindexer(1.0.volts))
    }

    /**
     * Class for the operator interface
     * getting inputs from controllers and whatnot.
     */
    object OI : SubsystemBase() {
        val operatorController = CommandXboxController(2) // todo
        val driverJoystickL = CommandJoystick(0) // todo
        val driverJoystickR = CommandJoystick(3) // todo

        /**
         * Allows you to tweak controller inputs (ie get rid of deadzone, make input more sensitive by squaring or cubing it, etc).
         */
        private fun Double.processInput(
            deadzone: Double = 0.1,
            squared: Boolean = false,
            cubed: Boolean = false,
            readjust: Boolean = true
        ): Double {
            var processed = this
            if (readjust) processed = ((this.absoluteValue - deadzone) / (1 - deadzone)) * this.sign
            return when {
                this.within(deadzone) -> 0.0
                squared -> processed.pow(2) * this.sign
                cubed -> processed.pow(3)
                else -> processed
            }
        }

        private fun Double.abs_GreaterThan(target: Double): Boolean {
            return this.absoluteValue > target
        }

        /**
         * Allows the inputted controller to rumble
         */
        class Rumble(
            val controller: CommandXboxController,
            val time: Double = 1.0,
            val rumblePower: Double = 1.0,
            val rumbleSide: GenericHID.RumbleType = GenericHID.RumbleType.kRightRumble
        ) : Command() {
            val timer = Timer()

            init {
                addRequirements(OI)
            }

            override fun initialize() {
                timer.restart(); controller.setRumble(rumbleSide, rumblePower)
            }

            override fun execute() {
                controller.setRumble(rumbleSide, rumblePower)
            }

            override fun end(interrupted: Boolean) {
                controller.setRumble(rumbleSide, 0.0)
            }

            override fun isFinished(): Boolean {
                return timer.hasElapsed(time)
            }
        }

        /**
         * Values for inputs go here
         */
        //===== DRIVETRAIN =====//
//        val driveForwards get() = driverController.leftY.processInput()
        val driveForwards get() = driverJoystickL.y.processInput()
        val driveStrafe get() = driverJoystickL.x.processInput()
        val rotateRobot get() = driverJoystickR.x.processInput()
        val slowMode get() = driverJoystickL.trigger().asBoolean
        val toggleFieldOriented get() = !driverJoystickR.trigger().asBoolean
        //===== SUBSYSTEMS =====//
        val lowerIntake get() = operatorController.povDown()
        val raiseIntake get() = operatorController.povUp()
        val runIntake get() = operatorController.povLeft()
        val runPreindexer get() = operatorController.povRight()
    }
}






































































































// uwu