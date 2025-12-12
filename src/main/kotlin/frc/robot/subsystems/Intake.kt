package frc.robot.subsystems

import beaverlib.controls.BeaverIdleMode
import beaverlib.controls.BeaverSparkMax
import beaverlib.utils.Units.Electrical.VoltageUnit
import com.revrobotics.spark.SparkLowLevel
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.engine.utils.initMotorControllers

object IntakeConstants {
    // pneumatics
    val leftReverse = 4
    val leftForward = 6
    val rightForward = 5
    val rightReverse = 7
    // intake motors, current, etc
    val intakeMotorID = 0 // todo
    val currentLimit = 30 // todo
}

object Intake : SubsystemBase() {
    // set up pneumatics
    val compressor = Compressor(PneumaticsModuleType.CTREPCM)
    val leftSolenoid = DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.leftReverse, IntakeConstants.leftForward)
    val rightSolenoid = DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.rightReverse, IntakeConstants.rightForward)
    // set up intake motor
    val intakeMotor = BeaverSparkMax(IntakeConstants.intakeMotorID, SparkLowLevel.MotorType.kBrushed)

    init {
        // enable pressure sensor closed loop (120PSI)
        compressor.enableDigital()
        // init motor controller
        initMotorControllers(IntakeConstants.currentLimit, BeaverIdleMode.COAST, intakeMotor)
    }

    /**
     * Sets the solenoid to either extended or not.
     * @param state The state of the solenoids, either extended or not.
     */
    fun setIntakePosition(state: DoubleSolenoid.Value) {
        leftSolenoid.set(state)
        rightSolenoid.set(state)
    }

    /**
     * Directly sets the voltage of the intake motor.
     * @param speed the voltage at which to run the intake at.
     */
    fun runIntake(speed: VoltageUnit) {
        intakeMotor.setVoltage(speed)
    }
}