package frc.robot.commands

import beaverlib.utils.Units.Electrical.VoltageUnit
import edu.wpi.first.units.measure.Voltage
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.Drivetrain

class swank() : Command() {
    init {
        addRequirements(Drivetrain)
    }

    override fun execute() {
        Drivetrain.rfDrive.set(10.0)
    }


}