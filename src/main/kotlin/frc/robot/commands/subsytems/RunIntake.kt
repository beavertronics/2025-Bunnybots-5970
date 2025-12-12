package frc.robot.commands.subsytems

import beaverlib.utils.Units.Electrical.VoltageUnit
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.Intake

class RunIntake(val speed: VoltageUnit) : Command() {

    init {
        addRequirements(Intake)
    }

    override fun execute() {
        Intake.runIntake(speed)
    }

    override fun isFinished(): Boolean {
        return false
    }
}