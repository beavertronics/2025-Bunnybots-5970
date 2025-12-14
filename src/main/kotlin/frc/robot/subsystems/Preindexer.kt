package frc.robot.subsystems

import beaverlib.controls.BeaverIdleMode
import beaverlib.controls.BeaverSparkMax
import beaverlib.utils.Units.Electrical.VoltageUnit
import com.revrobotics.spark.SparkLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.engine.utils.initMotorControllers

object PIConstants {
    val leftMotorID = 23 // todo
    val rightMotorID = 66 // todo
    val currentLimit = 30 // todo
}

object Preindexer : SubsystemBase() {
    val leftMotor = BeaverSparkMax(PIConstants.leftMotorID, SparkLowLevel.MotorType.kBrushless) // todo 775 or neo?
    val rightMotor = BeaverSparkMax(PIConstants.rightMotorID, SparkLowLevel.MotorType.kBrushless) // todo 775 or neo?

    init {
        initMotorControllers(PIConstants.currentLimit, BeaverIdleMode.COAST, leftMotor, rightMotor)
    }

    /**
     * Runs both of the preindexer motors at the set voltage.
     * @param speed the voltage to run the motors at.
     */
    fun runPreindexer(speed: VoltageUnit) {
        leftMotor.setVoltage(speed)
        rightMotor.setVoltage(speed)
    }
}