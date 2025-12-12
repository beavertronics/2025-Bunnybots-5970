package frc.robot.commands.subsytems

import beaverlib.utils.Units.Electrical.VoltageUnit
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.Preindexer

class RunPreindexer(val speed: VoltageUnit) : Command() {

    init {
        addRequirements(Preindexer)
    }

    override fun execute() {
        Preindexer.runPreindexer(speed)
    }

    override fun isFinished(): Boolean {
        return false
    }
}